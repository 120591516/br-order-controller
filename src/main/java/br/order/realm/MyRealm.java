package br.order.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.order.controller.common.CommonController;
import br.order.pojo.BrOperation;
import br.order.pojo.BrRolePermission;
import br.order.pojo.BrUser;
import br.order.service.BrOperationService;
import br.order.service.RoleManageService;
import br.order.service.RolePermissionService;
import br.order.service.UserManageService;
import br.order.vo.BrRoleVo;
import br.order.vo.BrUserVo;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserManageService userManageService;
	@Autowired
	private RoleManageService roleManageService;

	@Autowired
	private CommonController commonController;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private BrOperationService brOperationService;

	private static Logger logger = LoggerFactory.getLogger(MyRealm.class);

	/**
	 * 授权 为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 * @see 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持, 则可灵活决定是否启用AuthorizationCache
	 * @see 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权方法");

		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		List<Long> roleIdList = new ArrayList<Long>();
		/*
		 * // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next() String currentUsername = (String) super.getAvailablePrincipal(principals); if (StringUtils.isEmpty(currentUsername)) { return null; } // 从数据库中获取当前登录用户的详细信息 BrUser brUser = (BrUser) userManageService.getUserByUserName(currentUsername); BrUserVo user = new BrUserVo(); user.setUserId(brUser.getUserId()); user.setUserName(brUser.getUserName()); user.setUserLoginName(brUser.getUserLoginName());
		 * user.setUserPassword(brUser.getUserPassword()); user.setRoles(roleManageService.getRoleListByUserId(user.getUserId())); if (null != user) { // 实体类User中包含有用户角色的实体类信息 if (user != null && user.getRoles().size() > 0) { for (BrRole role : user.getRoles()) { // 获取当前登录用户的角色 roleList.add(role.getRoleName()); if(role!=null&&role.getRoleId()!=null){// 实体类Role中包含有角色权限的实体类信息 roleIdList.add(role.getRoleId()); } } List<UserPermissionVo>
		 * userPermissionVos=rolePermissionService.getPermissionListByRoleId(roleIdList); user.setUserPermissionVos(userPermissionVos); for(UserPermissionVo userPermissionVo:userPermissionVos){// if(userPermissionVo!=null){ permissionList.add(userPermissionVo.getPermissionName()); if(userPermissionVo.getChildrens()!=null&&userPermissionVo.getChildrens().size()>0){ for(BrPermission brPermission:userPermissionVo.getChildrens()){ permissionList.add(brPermission.getPermissionName()); }
		 * 
		 * } } } } } else { throw new AuthorizationException(); }
		 */

		BrUserVo user = ((BrUserVo) principals.getPrimaryPrincipal());
		if (null != user) {
			List<BrRoleVo> roleListByUserId = roleManageService.getRoleListByUserId(user.getUserId());
			user.setRoles(roleListByUserId);
			// 实体类User中包含有用户角色的实体类信息
			if (user.getRoles().size() > 0) {
				for (BrRoleVo role : user.getRoles()) { // 获取当前登录用户的角色
					roleList.add(role.getRoleName());
					if (role != null && role.getRoleId() != null) {// 实体类Role中包含有角色权限的实体类信息
						roleIdList.add(role.getRoleId());
					}
				}
				List<BrRolePermission> rolePermissionList = rolePermissionService.getRolePermissionsByRoleId(roleIdList);

				for (BrRolePermission rolePermission : rolePermissionList) {
					List<BrOperation> opList = brOperationService.getListByRole(rolePermission);
					for (BrOperation organizationOperation : opList) {
						permissionList.add(organizationOperation.getOperationDescribe());
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

	/**
	 * 认证 验证当前登录的Subject
	 * 
	 * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证方法");

		String username = token.getPrincipal().toString();
		String password = new String((char[]) token.getCredentials());

		BrUser brUser = userManageService.getUserByUserName(username);
		if (brUser == null)
			throw new UnknownAccountException("用户名或者密码出错");
		// Md5Hash md5 = new Md5Hash(password, username, 2);
		if (!brUser.getUserPassword().equals(password))
			throw new IncorrectCredentialsException("用户名或者密码出错");
		if (brUser.getUserStatus() != 0)
			throw new LockedAccountException("用户已经被锁定");
		BrUserVo user = new BrUserVo();
		try {
			BeanUtils.copyProperties(user, brUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), this.getName());
		info.setCredentialsSalt(ByteSource.Util.bytes(user.getUserName()));
		return info;
		/*
		 * // 获取基于用户名和密码的令牌 // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的 // 两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e UsernamePasswordToken token = (UsernamePasswordToken) authcToken; System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE)); if (StringUtils.isNotEmpty(token.getUsername())) { BrUser brUser = (BrUser) userManageService.getUserByUserName(token.getUsername()); BrUserVo
		 * user = new BrUserVo(); user.setUserId(brUser.getUserId()); user.setUserName(brUser.getUserName()); user.setUserLoginName(brUser.getUserLoginName()); user.setUserPassword(brUser.getUserPassword()); if (null != user) { AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserLoginName(), user.getUserPassword(),this.getName()); List<BrRoleVo> roleList = roleManageService.getRoleListByUserId(user.getUserId()); List<Long>rolelist=new ArrayList<Long>(); for (BrRoleVo brRoleVo
		 * : roleList) { if(brRoleVo!=null&&brRoleVo.getRoleId()!=null){ rolelist.add(brRoleVo.getRoleId()); } } List<UserPermissionVo> userPermissionVos=rolePermissionService.getPermissionListByRoleId(rolelist); user.setRoles(roleList); user.setUserPermissionVos(userPermissionVos); commonController.setSession("currentUser", user); return authcInfo; } } // //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常 return null;
		 */
	}

	/**
	 * 更新授权信息缓存
	 */
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("清除授权的缓存");
		/*
		 * Cache c = this.getAuthorizationCache(); Set<Object> keys = c.keys(); for (Object o : keys) { System.out.println("授权缓存:" + o + "-----" + c.get(o) + "----------"); }
		 */

		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		System.out.println("清除认证的缓存");
		/*
		 * Cache c = this.getAuthenticationCache(); Set<Object> keys = c.keys(); for (Object o : keys) { System.out.println("认证缓存:" + o + "----------" + c.get(o) + "----------"); }
		 */
		BrUserVo user = ((BrUserVo) principals.getPrimaryPrincipal());
		SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUserName(), getName());
		super.clearCachedAuthenticationInfo(spc);
	}

}
