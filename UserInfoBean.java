package simulateqq;
/*
 *UserInfoBean类是一个javaBean，每个对象负责存储一个用户的有关信息 */
public class UserInfoBean {
	private String name = null;
	private int qqnum = 0;
	private String password = null;
	private int status = 0;
	private String ip = null;
	private String info = null;
	private String pic = null;
	private String sex = null;
	private String email = null;
	private String place = null;
	private String birthday = null;
	private int port = 0;
	
	public UserInfoBean(){}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setQqnum(int qqnum){
		this.qqnum = qqnum; 
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setStatus(int Status){
		this.status = status;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public void setInfo(String info){
		this.info = info; 
	}
	
	public void setPic(String pic){
		this.pic = pic;
	}
	
	public void setSex(String sex){
		this.sex = sex;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public String getName(){
		return name;
	}
	
	public int getQqnum(){
		return qqnum;
	}
	
	public String getPassword(){
		return password;
	}
	
	public int getStatus(){
		return status;
	}
	
	public String getIp(){
		return ip;
	}
	
	public String getInfo(){
		return info;
	}
	
	public String getPic(){
		return pic;
	}
	
	public String getSex(){
		return sex;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPlace(){
		return place;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public int getPort(){
		return port;
	}
}

