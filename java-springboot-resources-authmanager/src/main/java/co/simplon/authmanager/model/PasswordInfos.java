package co.simplon.authmanager.model;

public class PasswordInfos {
	private String username;
	private String oldPassword;
	private String newPassword;
	private boolean forcePwdChange;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public boolean isForcePwdChange() {
		return forcePwdChange;
	}
	
	public void setForcePwdChange(boolean forcePwdChange) {
		this.forcePwdChange = forcePwdChange;
	}
}
