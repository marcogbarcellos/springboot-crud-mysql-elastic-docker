package com.userCrud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.userCrud.model.User;

/*
 * Running User CRUD Tests
 * By default, JUNIT cannot assure the order of the execution unless using the annotation
 * @FixMethodOrder(MethodSorters.NAME_ASCENDING) to order through NAME Ascending
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private String userJson(String name, String role) throws Exception {
    	User anObject = new User();
        anObject.setName(name);
        anObject.setRole(role);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(anObject );
    }
    
    private User[] getUsersFromJson(String json) throws Exception {
    	return (User[])(new ObjectMapper()).readValue(json, User[].class);
    }
    
    @Test
    public void insertUser1() throws Exception { 
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson("user1", "admin")))
            .andExpect(status().isCreated());
    }
    
    @Test
    public void insertUser1AgainShouldFail() throws Exception { 
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson("user1", "admin")))
            .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void insertUser2() throws Exception { 
       mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson("user2", "marketing")))
            .andExpect(status().isCreated());
    }
    
    @Test
    public void insertUser3() throws Exception { 
           	mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson("user3", "marketing")))
           .andExpect(status().isCreated());
    }
    
    @Test
    public void insertUser4() throws Exception { 
    	mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson("user4", "admin")))
            .andExpect(status().isCreated());
    }
    
    @Test
    public void insertUser5WithNullPropertiesShouldFail() throws Exception { 
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8)
            .content(userJson(null, null)))
            .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void shouldAReturnAllUsersSortedByUpdatedAtField() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	assertThat(users[0].getName()).isEqualTo("user4");
    	assertThat(users.length).isEqualTo(4);
    }
    
    @Test
    public void shouldBReturnOnlyUserWithAdminRole() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?role=admin"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isEqualTo(2);
    }
    
    @Test
    public void shouldCReturnOnlyUserWithMarketingRole() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?role=marketing"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isEqualTo(2);
    }
    
    @Test
    public void shouldDReturnNoneUserWithNonExistingRoleFilter() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?role=NONEXISTINGROLE"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isEqualTo(0);
    }
    
    @Test
    public void shouldEReturnUsersWithMultipleNamesOnFilter() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?name=user1,user2"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isEqualTo(2);
    }
    
    @Test
    public void shouldFReturnNoneUserWithMultipleNamesButNonExistinroleOnFilter() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?name=user1,user2&role=NONEXISTING"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isEqualTo(0);
    }
    
    @Test
    public void shouldGReturnUsersWithMultipleNamesAndMatchingRolesOnFilter() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users?name=user1,user4&role=admin"))
      	.andDo(print()) 
      	.andExpect(status().isOk())
      	.andReturn();
    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	System.out.println("Auhsahusahus: "+users[0]);
    	assertThat(users.length).isEqualTo(2);
    }
    
    @Test
    public void shouldHGetUserByIdChangePropertyAndCheckIfPropertyChanged() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users"))
    	      	.andDo(print()) 
    	      	.andExpect(status().isOk())
    	      	.andReturn();
    	    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isGreaterThan(0);
    	    	
    	mockMvc.perform(put("/users/"+users[0].getId())
    			.contentType(APPLICATION_JSON_UTF8)
                .content(userJson("userNew", null)))
                .andExpect(status().isOk());
    	
    	MvcResult resultFindById = mockMvc.perform(get("/users/"+users[0].getId()))
    			.andExpect(status().isOk())
                .andReturn();
    	User user = (new ObjectMapper()).readValue(resultFindById .getResponse().getContentAsString(), User.class);
    	assertThat(user.getName()).isEqualTo("userNew");
    	assertThat(user.getId()).isEqualTo(users[0].getId());
    	assertThat(user.getUpdatedAt()).isAfter(users[0].getUpdatedAt());
    }
    
    @Test
    public void shouldITryToDeleteUnexistentUserButFail() throws Exception {
    	mockMvc.perform(delete("/users/UNEXISTENTID"))
    			.andExpect(status().is4xxClientError());
    }	
    
    @Test
    public void shouldJGetUserByIdAndDelete() throws Exception {
    	MvcResult result = this.mockMvc.perform(get("/users"))
    	      	.andDo(print()) 
    	      	.andExpect(status().isOk())
    	      	.andReturn();
    	    	
    	User[] users = getUsersFromJson(result.getResponse().getContentAsString());
    	
    	assertThat(users.length).isGreaterThan(0);
    	
    	User userToDelete = users[0];
    	    	
    	mockMvc.perform(delete("/users/"+userToDelete.getId()))
    			.andExpect(status().is2xxSuccessful());
    }
    
}

