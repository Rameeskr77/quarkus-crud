package org.acme.Controller;

import org.acme.model.Films;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/movie")
public class movie {
  

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getmovie(){
     List<Films> list  = Films.listAll();
        return Response.ok(list).build();
    }
@POST
@Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createmovie(Films moview){
    System.out.println(moview);
       Films.persist(moview);
       if(moview.isPersistent()){
           return Response.created(URI.create(
                   "/movie"+moview.id
           )).build();
       }
        return Response.status(Response.Status.BAD_REQUEST).build();
}

@DELETE
@Transactional
@Path("{id}")
@Consumes(MediaType.APPLICATION_JSON)
public Response delete(@PathParam("id")long id){
    boolean result = Films.deleteById(id);
    if(result){
        return Response.noContent().build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
}
@GET
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response findone(@PathParam("id") long id){
 return   Films.findByIdOptional(id)
         .map(mo ->Response.ok(mo).build() )
         .orElse(Response.status(Response.Status.BAD_REQUEST).build());
}

@PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Films films){
    int var = Films.update("name=?1,description=?2 where id=?3", films.name, films.description, films.id);
    List.of(var)
            .stream()
            .findFirst()
            .map(integer -> {
                if(integer==0){
                    Films  f1=new Films();
                    f1.name= films.name;
                    f1.description=films.description;
                    Films.persist(f1);
                }return Response.noContent().build();
            });
    return Response.noContent().build();

}
}
