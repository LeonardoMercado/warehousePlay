package controllers;

import models.Product;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import views.html.products.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Products extends Controller {
    private final Form<Product> productoForm;
    private MessagesApi messagesApi;
    public final static ArrayList<Lang> idioma = new ArrayList<Lang>(){{
        add(Lang.forCode("es"));
    }};
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    public Products(FormFactory formFactory, MessagesApi messagesApi) {
        this.productoForm = formFactory.form(Product.class);
        this.messagesApi = messagesApi;
    }

    public Result list(){
        return ok(list.render("Produtcs Catalogue",Product.findAll()));
    }

    public Result newProduct(){
        return ok(details.render("Detalle del producto",productoForm, messagesApi.preferred(idioma)));
    }

    public Result details(String ean){
        return ok("llegamos al detalle del producto");
    }

    public Result save(Http.Request request){
        Form<Product> boundForm = productoForm.bindFromRequest(request);
        if(boundForm.hasErrors()){
            logger.error("error = {}",boundForm.errors());
            return badRequest(details.render("Error",boundForm,messagesApi.preferred(idioma)));
        }
        Product product = boundForm.get();
        product.save();
        return ok(list.render("Produtcs added",Product.findAll()));
    }
}
