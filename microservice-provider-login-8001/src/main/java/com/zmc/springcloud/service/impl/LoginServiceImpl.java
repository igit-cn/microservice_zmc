package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyAuthority;
import com.zmc.springcloud.entity.HyRole;
import com.zmc.springcloud.entity.HyRoleAuthority;
import com.zmc.springcloud.mapper.LoginMapper;
import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.service.LoginService;
import com.zmc.springcloud.utils.CheckedOperation;
import com.zmc.springcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.zmc.springcloud.utils.CommonAttributes.ROOT_ROLE;

/**
 * Created by xyy on 2018/11/20.
 *
 * @author xyy
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public HyAdmin getByUserName(String username) {
        return loginMapper.findByUserName(username);
    }

    @Override
    public void insertHyAdmin(HyAdmin hyAdmin) {
        loginMapper.insertHyAdmin(hyAdmin);
    }

    @Override
    public Boolean loginCheck(HyAdmin hyAdmin) throws Exception{
        String userName = hyAdmin.getUsername();
        HyAdmin admin = loginMapper.findByUserName(userName);
        if (admin == null || !admin.getIsEnabled()) {
            return false;
        }
        String encryptpw = StringUtil.encodePassword(hyAdmin.getPassword(),
                "MD5");
        if (admin.getPassword().equalsIgnoreCase(encryptpw)) {
            return true;
        }
        return false;
    }

    @Override
    public HashMap<String, Object> getMenu(String username) throws Exception{
        HashMap<String, Object> obj = new HashMap<>();
        HyAdmin admin = loginMapper.findByUserName(username);
        Long roleId = admin.getRole();
        if(roleId == null){
            return null;
        }
        HyRole role = loginMapper.findRoleById(roleId);
        if(role == null){
            return null;
        }

        List<HashMap<String, Object>> listhashmap = new ArrayList<>();
        // 管理员显示全部
        if(ROOT_ROLE.equalsIgnoreCase(role.getName())){
            //1.获取menu
            List<HashMap<String, Object>> lh = new ArrayList<>();
            List<HyAuthority> as = loginMapper.findAllAuthority();
            for (HyAuthority au : as) {
                if(au.getPid() == null && au.getIsDisplay()) {
                    LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
                    hm.put("fullUrl", au.getFullUrl());
                    hm.put("icon", au.getIcon());
                    hm.put("id", au.getId());
                    hm.put("isDisplay", au.getIsDisplay());
                    hm.put("name", au.getName());
                    hm.put("operation", au.getOperation());
                    hm.put("path", au.getUrl());
                    hm.put("range", au.getRange());
                    hm.put("requestUrl", au.getRequestUrl());
                    hm.put("children", addChildren(au, role));
                    lh.add(hm);
                }
            }
            obj.put("menu", lh);
            //2.获取checkedRange
            for (HyAuthority au : as) {
                if(au.getIsDisplay()) {
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("fullUrl", au.getFullUrl());
                    hm.put("co", CheckedOperation.edit);
                    hm.put("id", au.getId());
                    listhashmap.add(hm);
                }
            }
            obj.put("checkedRange", listhashmap);
        }else{
            //非管理员 根据角色找到权限
            //获取menu和checkRange
            List<HyRoleAuthority> roleAuthorities = loginMapper.findHyRoleAuthorityByRoleId(roleId);
            if(roleAuthorities.size() == 0){
                return null;
            }

            //1.获取menu
            List<HashMap<String, Object>> lh = new ArrayList<>();
            for (HyRoleAuthority a : roleAuthorities) {
                Long hyAuthorityId = a.getAuthoritys();
                HyAuthority au = loginMapper.findHyAuthorityById(hyAuthorityId);

                if(au.getPid() == null && au.getIsDisplay()){
                    LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
                    hm.put("fullUrl", au.getFullUrl());
                    hm.put("icon", au.getIcon());
                    hm.put("id", au.getId());
                    hm.put("isDisplay", au.getIsDisplay());
                    hm.put("name", au.getName());
                    hm.put("operation", au.getOperation());
                    hm.put("path", au.getUrl());
                    hm.put("range", au.getRange());
                    hm.put("requestUrl", au.getRequestUrl());
                    hm.put("children", addChildren(au, role));
                    lh.add(hm);
                }
            }
            obj.put("menu", lh);

            //2.获取checkedRange
            for (HyRoleAuthority a : roleAuthorities) {
                Long hyAuthorityId = a.getAuthoritys();
                HyAuthority  au = loginMapper.findHyAuthorityById(hyAuthorityId);
                if(au.getIsDisplay()){
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("fullUrl", au.getFullUrl());
                    hm.put("co", a.getOperationCheckedNumber());
                    hm.put("id", au.getId());
                    listhashmap.add(hm);
                }
            }
            obj.put("checkedRange", listhashmap);
        }

        //原来的currentUser
        Map<String, Object> map = new HashMap();
        map.put("name", admin.getUsername());
        map.put("realName", admin.getName());
        map.put("avatar", null);
        map.put("userid", null);
        map.put("notifyCount", null);
        obj.put("currentuser", map);

        return obj;
    }

    @Override
    public Set<HyRole> getSubRoles(String username) throws Exception {
        List<HyRole> list = loginMapper.getSubRoles(username);
        return new HashSet<>(list);
    }

    /** 构建左侧菜单栏级联关系*/
    private List<HashMap<String, Object>> addChildren(HyAuthority parent, HyRole role) throws Exception{
        List<HashMap<String, Object>> list = new ArrayList();
        List<HyAuthority> childrenHyAuthorities = loginMapper.findHyAuthorityChildrenByPid(parent.getId());
        if(!childrenHyAuthorities.isEmpty()){
            for (HyAuthority child : childrenHyAuthorities) {
                LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
                // 判断是否有子权限
                boolean hasSubAuthority;
                if(ROOT_ROLE.equalsIgnoreCase(role.getName())){
                    // admin有所有的权限
                    hasSubAuthority = true;
                }else{
                    List<HyRoleAuthority> ra = loginMapper.findHyRoleAuthorityByRoleIdAndAuthorityId(role.getId(), child.getId());
                    hasSubAuthority = !ra.isEmpty();
                }
                if(child.getIsDisplay() && hasSubAuthority){
                    hm.put("fullUrl", child.getFullUrl());
                    hm.put("icon", child.getIcon());
                    hm.put("id", child.getId());
                    hm.put("isDisplay", child.getIsDisplay());
                    hm.put("name", child.getName());
                    hm.put("operation", child.getOperation());
                    hm.put("path", child.getUrl());
                    hm.put("range", child.getRange());
                    hm.put("requestUrl", child.getRequestUrl());
                    hm.put("children", addChildren(child, role));
                    list.add(hm);
                }
            }
        }
        return list;
    }
}
