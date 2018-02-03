package model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerDAO customerDao;
	
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if(newPassword!=null && newPassword.length()!=0) {
				byte[] temp = newPassword.getBytes();
				return customerDao.update(
						temp, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
	
	@Transactional(readOnly=true)
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if(password!=null && password.length()!=0) {
				byte[] temp = password.getBytes();		//使用者輸入
				byte[] pass = bean.getPassword();		//資料庫抓出
				if(Arrays.equals(pass, temp)) {
					return bean;
				}
			}
		}
		return null;
	}
}
