package Server;

public class UserData {
	
	private String nickname, password;
	
	public UserData(){}
	
	public UserData(String nickname, String password){
		this.nickname = nickname;
		this.password = password;
	};
	

	public void print() {
		System.out.println(this.nickname + "|" + this.password);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
