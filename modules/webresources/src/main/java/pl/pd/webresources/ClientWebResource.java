package pl.pd.webresources;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import pl.pd.emir.entity.Client;
import pl.pd.emir.register.ClientManager;

/**
 *
 * @author PawelDudek
 */
//@Stateless
@Path("/client")
//TODO obejscie problemu @Stateless na GF 4.1
@RequestScoped
public class ClientWebResource {

    @EJB
    private ClientManager clientManager;

    @GET
    @Produces("application/json")
    public List<Client> getClients() {
        return clientManager.findAll();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Client getClient(@PathParam("id") Long id) {
        return clientManager.getById(id);
    }

}
