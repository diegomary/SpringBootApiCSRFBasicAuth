package dm88;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@RestController
public class ApiController {	

	
	@GetMapping("/api/gettoken")
	public String gettoken(HttpServletRequest request,HttpServletResponse response)	
	{
		
		String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
		CsrfToken token = (CsrfToken) request.getSession().getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);				
		return token.getToken();  		
		
	} 
	
	
	@PostMapping("/api/setcart")
	public String setcart(String cart)	
	{		
		Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();			
		return gson.toJson("Hello all is good");
	} 
	
	
	
	  @RequestMapping("/api/getsc")

	  public String ret1(HttpSession session) {	  
		  
		  Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();			
		  return gson.toJson("Getting the session");
		
	  } 
	
	
		@PostMapping("/api/postsc")
		public String postcart(String cart)	
		{		
			Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();			
			return gson.toJson("Posting the session");
		} 
	
		
		
	
	class test
	{
		public String datum;
		public String[] test =  {"Pallone", "ziopluto"};
	}

  @Autowired private MongoTemplate mongoTemplate;

  @RequestMapping(value = "/api/saveobj",method = RequestMethod.GET)
    public String home(HttpSession session) {
	  
	  session.setAttribute("test", "Hello this is a test");	  
	  test t = new test();
	  t.datum="hello";
      DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value").add("topo", "squalo").add("obj", t)
                .get();
      mongoTemplate.save(objectToSave, "newcoll");            
      return "Ok";
        
    }  

  @RequestMapping("/api/getall")

  public String ret(HttpSession session) {	  
	  
	Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();	
	session.setAttribute("flowers", gson.toJson(mongoTemplate.findAll(DBObject.class, "BachFlowers")));
	return gson.toJson(mongoTemplate.findAll(DBObject.class, "BachFlowers"));	
	
  } 
  
  @CrossOrigin(origins = {"*"},methods = { RequestMethod.GET,
  RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.OPTIONS},
  allowedHeaders= {"content-type","custom-header"})
  @RequestMapping("/api/findone")
  public String fone(HttpSession session) {  
	  
	Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
	Query query = new Query();
	query.addCriteria(Criteria.where("Name").regex("^A"));	
	return gson.toJson(mongoTemplate.find(query,DBObject.class, "BachFlowers"));
	
  } 
  
  
  @RequestMapping("/api/session")
  public String retsess(HttpSession session) {	
	return session.getAttribute("test").toString();
  }
  
  @RequestMapping("/api/session1")
  public String retsess1(HttpSession session) {		
	return session.getAttribute("flowers").toString();
  }  

}
