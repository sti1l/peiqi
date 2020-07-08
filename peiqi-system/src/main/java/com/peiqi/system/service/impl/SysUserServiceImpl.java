package com.peiqi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peiqi.hibernate.dao.ReadAndWriteObjectDao;
import com.peiqi.system.domain.SysUser;
import com.peiqi.system.domain.SysUserRole;
import com.peiqi.system.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {
	
	@Autowired
	private ReadAndWriteObjectDao readAndWriteObjectDao;

	@Override
	public List<SysUser> selectUserList(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysUser> selectAllocatedList(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysUser> selectUnallocatedList(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUser selectUserByLoginName(String loginName) {
		String sql = "select * from sys_user where login_name = ?";
		List<Object> preParameters = new ArrayList<>();
		preParameters.add(loginName);
		SysUser sysUser = readAndWriteObjectDao.getObjectBySql(sql, SysUser.class, preParameters);
		return sysUser;
	}

	@Override
	public SysUser selectUserByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUser selectUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUser selectUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysUserRole> selectUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteUserById(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUserByIds(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUser(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean registerUser(SysUser user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updateUser(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUserInfo(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertUserAuth(Long userId, Long[] roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public int resetUserPwd(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String checkLoginNameUnique(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkPhoneUnique(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkEmailUnique(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkUserAllowed(SysUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public String selectUserRoleGroup(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectUserPostGroup(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int changeStatus(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
