package org.kenneth.ctx.news.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Administrator on 2015/1/16.
 */
public class SecurityUtils {

    /**
     * 获取当前认证通过的用户名
     *
     * @return
     */
    public static String findAuthenticatedUsername() {
        String username = null;
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
