package aplicacao.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aplicacao.model.Cadastro;
import aplicacao.model.Login;
import aplicacao.model.Sessao;
import aplicacao.repository.CadastroRepositorio;
import aplicacao.security.JwtConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginService {
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Sessao logar (Login login) {
		if(login == null || login.getLogin() == null || login.getSenha() == null) {
			throw new RuntimeException("login e senha são requeridos");
		}
		
		Cadastro cadastro = cadastroRepository.findByLoginLogin(login.getLogin());
		boolean passwordMatches = encoder.matches(login.getSenha(), cadastro.getLogin().getSenha());
		if(!passwordMatches) {
			throw new RuntimeException("Login ou senha incorretos");
		}
		
		return iniciarSessao(cadastro.getLogin().getLogin());
		
		
	}
	
	public Sessao iniciarSessao(String login) {
		if(login == null)
			throw new RuntimeException("login vazio, não é possível iniciar sessão");
		Sessao sessao = new Sessao(
				login,
				new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + JwtConstants.TOKEN_EXPIRATION)
				);
		sessao.setToken(JwtConstants.PREFIX + getJwtToken(sessao));
		return sessao;	
	}
	
	private String getJwtToken(Sessao sessao) {
		String role = "ROLE_USER";
		List<GrantedAuthority> grantedAuthority = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
		String token = Jwts.builder().setSubject(sessao.getLogin())
				.claim("authorities",
						grantedAuthority.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(sessao.getDataInicio()).setExpiration(sessao.getDataFim())
				.signWith(SignatureAlgorithm.HS512, JwtConstants.KEY.getBytes()).compact();
		return token;
	}

}
