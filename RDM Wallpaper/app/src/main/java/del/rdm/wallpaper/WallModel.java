package del.rdm.wallpaper;

public class WallModel {
    private String id;
	private String name;
	private String URL;
	
	public WallModel(){
		
	}
	
	public WallModel(String id, String name, String URL){
		this.id = id;
		this.name = name;
		this.URL = URL;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	public void setUrl(String URL){
		this.URL = URL;
	}

	public String getURL(){
		return URL;
	}

	@Override
	public String toString() {
		
		return id + name + URL;
		
	}
}
