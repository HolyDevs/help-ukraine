package help.ukraine.app.security.model;

import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.AuthRoles;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private UserModel userModel;

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        String oAuthRole = getOAuthRole();
        return Set.of(new SimpleGrantedAuthority(oAuthRole));
    }

    public Long getId() {
        return userModel.getId();
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getOAuthRole() {
        return switch (userModel.getAccountType()) {
            case REFUGEE -> AuthRoles.REFUGEE_ROLE;
            case HOST -> AuthRoles.HOST_ROLE;
        };
    }
}
