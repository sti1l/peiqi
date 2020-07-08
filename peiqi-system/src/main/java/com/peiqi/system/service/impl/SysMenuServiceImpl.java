package com.peiqi.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peiqi.common.core.domain.Ztree;
import com.peiqi.hibernate.dao.ReadObjectDao;
import com.peiqi.system.domain.SysMenu;
import com.peiqi.system.domain.SysRole;
import com.peiqi.system.domain.SysUser;
import com.peiqi.system.service.ISysMenuService;

/**
 * 菜单 业务层处理
 * 
 * @author STILL
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
	
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Autowired
	private ReadObjectDao readObjectDao;

	/**
	 * 根据用户查询菜单
	 * 
	 * @param user 用户信息
	 * @return 菜单列表
	 */
	@Override
	public List<SysMenu> selectMenusByUser(SysUser user) {
		List<SysMenu> menus = new LinkedList<SysMenu>();
		String sql_selectMenu = null;
		ArrayList<Object> preParameters = null;
		// 管理员显示所有菜单信息
		if (user.isAdmin()) {
			sql_selectMenu = this.getSql_selectMenu(null);
		} else {
			sql_selectMenu = this.getSql_selectMenu(user.getUserId());
			preParameters = new ArrayList<>();
			preParameters.add(user.getUserId());
		}
		menus = readObjectDao.getObjectsBySql(sql_selectMenu, SysMenu.class, null, null, preParameters);
		return getChildPerms(menus, 0);
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list     分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
		List<SysMenu> returnList = new ArrayList<SysMenu>();
		for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();) {
			SysMenu t = (SysMenu) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * 
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<SysMenu> list, SysMenu t) {
		// 得到子节点列表
		List<SysMenu> childList = getChildList(list, t);
		t.setChildren((ArrayList<SysMenu>) childList);
		for (SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				Iterator<SysMenu> it = childList.iterator();
				while (it.hasNext()) {
					SysMenu n = (SysMenu) it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
		List<SysMenu> tlist = new ArrayList<SysMenu>();
		Iterator<SysMenu> it = list.iterator();
		while (it.hasNext()) {
			SysMenu n = (SysMenu) it.next();
			if (n.getParentId().longValue() == t.getMenuId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}

	@Override
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysMenu> selectMenuAll(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> selectPermsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ztree> roleMenuTreeData(SysRole role, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ztree> menuTreeData(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> selectPermsAll(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteMenuById(Long menuId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SysMenu selectMenuById(Long menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCountMenuByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectCountRoleMenuByMenuId(Long menuId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMenu(SysMenu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMenu(SysMenu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String checkMenuNameUnique(SysMenu menu) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取菜单列表查询语句
	 * 
	 * @param userId
	 * @return
	 */
	private String getSql_selectMenu(Long userId) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT ");
		builder.append("	m.menu_id, ");
		builder.append("	m.parent_id, ");
		builder.append("	m.menu_name, ");
		builder.append("	m.url, ");
		builder.append("	m.visible, ");
		builder.append("	ifnull( m.perms, '' ) AS perms, ");
		builder.append("	m.target, ");
		builder.append("	m.menu_type, ");
		builder.append("	m.icon, ");
		builder.append("	m.order_num, ");
		builder.append("	m.create_time  ");
		builder.append("FROM ");
		builder.append("	sys_menu m ");
		if (userId != null) {
			builder.append("	LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id ");
			builder.append("	LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id ");
			builder.append("	LEFT JOIN sys_role ro ON ur.role_id = ro.role_id  ");
			builder.append("WHERE ");
			builder.append("	ur.user_id = ? ");
			builder.append("AND m.menu_type in ('M', 'C') and m.visible = 0 AND ro.status = 0 ");
		} else {
			builder.append("WHERE m.menu_type in ('M', 'C') and m.visible = 0 ");
		}
		builder.append("ORDER BY ");
		builder.append("	m.parent_id, ");
		builder.append("	m.order_num; ");
		return builder.toString();
	}
}
