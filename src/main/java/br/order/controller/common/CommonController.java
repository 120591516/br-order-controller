package br.order.controller.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import br.order.vo.BrUserVo;
/**
 * CommonController
 * @ClassName: CommonController
 * @Description: TODO(CommonController)
 * @author adminis
 * @date 2016年9月13日 下午3:32:43
 *
 */
@Repository
public class CommonController {

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * @Title: setSession
	 * @Description: TODO(将一些数据放到ShiroSession中,以便于其它地方使用)
	 * @param @param key
	 * @param @param value    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/**
	 * 获取session中用户信息
	 * @Title: getUserBySession
	 * @Description: TODO(获取session中用户信息)
	 * @param @return    设定文件
	 * @return BrUserVo    返回类型
	 * @throws
	 */
	public BrUserVo getUserBySession() {
		BrUserVo user = null;
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			user = (BrUserVo) subject.getPrincipals().asList().get(0);
		}
		return user;
	}
	
}
