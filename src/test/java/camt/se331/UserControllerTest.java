package camt.se331;

import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.repository.UserRepository;
import camt.se331.shoppingcart.service.ProductService;
import camt.se331.shoppingcart.service.UserService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Bitee on 5/21/2016.
 */
public class UserControllerTest {

    @Test
    public void Register () {
        User user = new User(1l, "Narutchai", "muzukashii", "bitee4183@gmail.com", "Bitee4183");
        MatcherAssert.assertThat(user.getId(), Matchers.is(1l));
        MatcherAssert.assertThat(user.getName(), Matchers.is("Kill"));
        MatcherAssert.assertThat(user.getUsername(), Matchers.is("muzukashii"));
        MatcherAssert.assertThat(user.getEmail(), Matchers.is("bitee4183@gmail.com"));
        MatcherAssert.assertThat(user.getPassword(), Matchers.is("Bitee4183"));

    }

//    @Autowired
//    private ProductService productService;
//
//    @Test
//    public void Test(){
//        OngoingStubbing<List<Product>> call = Mockito.when(productService.getProductsByName("Some Name")).thenAnswer("Call");
//        List<Product> testName = productService.getProductsByName("Some Name");
//        List<Product> Test = List< org.junit.Test>
//        Assert.assertEquals("Call",testName);
//
//    }

    @Test
    public void TestFindOne (){
        UserRepository userRepo = mock(UserRepository.class);

        when(userRepo.findOne((long)0)).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                Long id = (Long) args[0];
                return new User(id,"name","username","email","password");
            }

        });
    }

}
