package pojoPack;

public class GetCourse {

	// create getcourse pojo class and creare valiable of json as valiables
	// create getter and setter for valiable. short cut is alt+shift+S
	// since course contains sub jason creare a subclass and addd reutn type here
	private String url;
	private String services;
	private String expertise;
	private Courses courses;
	private String instructor;
	private String linkdIn;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public Courses getCourses() {
		return courses;
	}
	public void setCourses(Courses courses) {
		this.courses = courses;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getLinkdIn() {
		return linkdIn;
	}
	public void setLinkdIn(String linkdIn) {
		this.linkdIn = linkdIn;
	}
	
}
