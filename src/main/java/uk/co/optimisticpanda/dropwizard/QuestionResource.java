package uk.co.optimisticpanda.dropwizard;
import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.co.optimisticpanda.dropwizard.dao.QuestionDao;
import uk.co.optimisticpanda.dropwizard.domain.Question;
import uk.co.optimisticpanda.dropwizard.views.QuestionView;

import com.google.common.base.Preconditions;
import com.yammer.metrics.annotation.Timed;

@Path("/question")
@Produces({MediaType.TEXT_HTML})
public class QuestionResource {

    private final QuestionDao dao;
	public QuestionResource (QuestionDao dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    @Path("/{id}")
    public QuestionView getQuestion(@PathParam("id") long id) {
        return new QuestionView(dao.get(id));
    }
    
    @GET
    @Timed
    @Path("/")
    public Collection<Question<?>> getQuestions() {
    	return dao.getAll();
    }
    
    @POST
    @Timed
    @Path("/{id}")
    public void updateQuestion(@PathParam("id") long id, Question<?> question) {
    	Preconditions.checkArgument(id == question.getId(), "Trying to update wrong environment.");
    	dao.update(question.getType(), question);
    }
    
    @POST
    @Timed
    @Path("/")
    public void addQuestion(Question<?> question) {
    	dao.insert(question.getType(), question);
    }
    
    @DELETE
    @Timed
    @Path("/{id}")
    public void deleteQuestion(@PathParam("id") long id) {
    	dao.deleteQuestion(id);
    }
    
}
