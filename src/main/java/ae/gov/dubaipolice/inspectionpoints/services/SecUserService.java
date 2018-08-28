package ae.gov.dubaipolice.inspectionpoints.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ae.gov.dubaipolice.base.model.LoginUser;
import ae.gov.dubaipolice.base.security.UserService;

@Service
@Transactional
public class SecUserService implements UserService {
	static final Logger LOGGER = LoggerFactory.getLogger(SecUserService.class);
	@Autowired
	private BeanFactory beanFactory;
	@Override
	public LoginUser findUser(String userName, String password) {
		LOGGER.info("<----findUser block start----->");
		UserService userService = (UserService) this.beanFactory.getBean("userServiceImpl", UserService.class);
		LoginUser validDubaiPoliceUser = userService.findUser(userName, password);
		if (validDubaiPoliceUser != null) {
			return validDubaiPoliceUser;
			/*
			Airwinguser airwinguser = airwingUserRepository.findByUserid(validDubaiPoliceUser.getGrpNumber());
			LOGGER.info("<---Login Success---->");
			if (airwinguser == null) {
				LOGGER.info("<---Login Failed---->");
				throw new UsernameNotFoundException("User doesn't exist in airwings system");
			}
			if (airwinguser.getGrouptype() != null) {
				UserRoles roles = new UserRoles(airwinguser.getGrouptype().getGroupName());
				roles.setId(airwinguser.getGrouptype().getId());
				List<UserRoles> list = new ArrayList<>();
				list.add(roles);
				validDubaiPoliceUser.setUserRoles(list);
			}
			
		*/}
		return null;
	}

}
