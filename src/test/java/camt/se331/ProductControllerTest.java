package camt.se331;

import camt.se331.shoppingcart.controller.ProductController;
import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.entity.SelectedProduct;
import camt.se331.shoppingcart.entity.ShoppingCart;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.repository.ProductRepository;
import camt.se331.shoppingcart.repository.UserRepository;
import camt.se331.shoppingcart.service.ProductService;
import camt.se331.shoppingcart.service.ProductServiceImpl;
import camt.se331.shoppingcart.service.UserService;
import camt.se331.shoppingcart.service.UserServiceImpl;
import com.mysql.fabric.Response;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jws.soap.SOAPBinding;
import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Bitee on 5/21/2016.
 */
public class ProductControllerTest {


        private Product product = new Product(20l,"Snack","Delicious",6900.00,6000.00);


        @Test
        public void testGetTotalProductPrice () {

        Product Total = new Product(product);
        SelectedProduct sp1 = new SelectedProduct(Total, 0);
        MatcherAssert.assertThat(sp1.getTotalPrice(), Matchers.is(0.0));
            sp1.setAmount(1);
        MatcherAssert.assertThat(sp1.getTotalPrice(), Matchers.is(6900.00));
            sp1.setAmount(2);
        MatcherAssert.assertThat(sp1.getTotalPrice(), Matchers.is(13800.0));
    }

    @Test
    public void testGetWholeSalePrice () {
        Product Wholesale = new Product(product);
        SelectedProduct sp2 = new SelectedProduct(Wholesale, 0);
        MatcherAssert.assertThat(sp2.getWholeSalePrice(), Matchers.is(0.0));
        sp2.setAmount(1);
        MatcherAssert.assertThat(sp2.getWholeSalePrice(), Matchers.is(50.00));
        sp2.setAmount(2);
        MatcherAssert.assertThat(sp2.getWholeSalePrice(), Matchers.is(12000.00));
    }

        @Test
        public void AddProductAndGetProduct () {
        Product add = new Product(product);
            MatcherAssert.assertThat(add.getId(), Matchers.is(20l));
            MatcherAssert.assertThat(add.getName(), Matchers.is("Snack"));
            MatcherAssert.assertThat(add.getDescription(), Matchers.is("Delicious"));
            MatcherAssert.assertThat(add.getTotalPrice(), Matchers.is(6900.00));
            MatcherAssert.assertThat(add.getWholesalePrice(), Matchers.is(6000.00));
    }





}
