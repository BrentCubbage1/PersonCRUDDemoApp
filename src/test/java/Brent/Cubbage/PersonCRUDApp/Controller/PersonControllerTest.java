package Brent.Cubbage.PersonCRUDApp.Controller;

import Brent.Cubbage.PersonCRUDApp.Entity.Person;
import Brent.Cubbage.PersonCRUDApp.Repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

//Annotate tests with SpringBootTests, AutoConfigureMockMVC, RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository repository;

    @Test
    public void testReadById() throws Exception {
        Long id = 1L;
        BDDMockito.given(repository.findById(id))
                .willReturn(Optional.of(new Person(null, "Person")));

        //This is a string I make of the JSON return
        String expectedContent = "{\"id\":null,\"name\":\"Person\"}";

        //take our Mock MVC we made and build the request.
        this.mvc.perform(MockMvcRequestBuilders

                        //It is a GET request, so we use .get to hit the endpoint
                        .get("/person/read/" + id))

                //And when we hit the endpoint, we expect 1. The status to be OK and
                .andExpect(MockMvcResultMatchers.status().isOk())

                //2. we also expect the content to match the expectedContent, because that is the person we put in the repository.
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }

    //and we use the above notes to write the create test.
    @Test
    public void createTest() throws Exception {
        Person person = new Person(null, "Brent");
        BDDMockito.given(repository.save(person))
                .willReturn(person);

        String expectedContent = "{\"id\":null,\"name\":\"Brent\"}";

        //The MVC perform method is different. We have to specify three things
        //1. The content being entered into the create method.
        //2. That it accepts the JSON MediaType.
        //and 3. That the content being entered is of type JSON.

        this.mvc.perform(MockMvcRequestBuilders.post("/person/create")
                        .content(expectedContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))

                //We still expect the same things, status and Content.
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void updateTest() throws Exception {
        Person person = new Person(1L, "Person");
        Person update = new Person(1L, "Brent");

        BDDMockito.given(repository.findById(1L)).willReturn(Optional.of(person));
        BDDMockito.given(repository.save(update)).willReturn(update);

        String expectedContent = "{\"id\":1,\"name\":\"Brent\"}";

        this.mvc.perform(MockMvcRequestBuilders.put("/person/update/" + 1)
                .content(expectedContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));


    }


}
