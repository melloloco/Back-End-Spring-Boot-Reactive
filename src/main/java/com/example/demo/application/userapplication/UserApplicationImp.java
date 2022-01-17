package com.example.demo.application.userapplication;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.example.demo.core.ApplicationBase;
import com.example.demo.core.configurationBeans.NanoIdUtils;
import com.example.demo.domain.userdomain.User;
import com.example.demo.domain.userdomain.UserProjection;
import com.example.demo.domain.userdomain.UserReadRepository;
import com.example.demo.domain.userdomain.UserWriteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserApplicationImp extends ApplicationBase<User, UUID> implements UserApplication{
    private final UserWriteRepository userWriteRepository;
    private final UserReadRepository userReadRepository;
    private final ModelMapper modelMapper;
    private final Logger logger;

    @Autowired
    public UserApplicationImp (final UserWriteRepository userWriteRepository, final UserReadRepository userReadRepository, 
    final ModelMapper modelMapper, final Logger logger){
        super((id) -> userWriteRepository.findById(id));
        this.userReadRepository = userReadRepository;
        this.userWriteRepository = userWriteRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
        }

@Override
public Mono<UserDTOOut> add(UserDTOIn userDTOIn) {
    User user = modelMapper.map(userDTOIn, User.class);
    user.setId(UUID.randomUUID());
    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    user.setProvider("logging");
    user.setThisNew(true);
    UserDTOOut userDto = this.modelMapper.map(user, UserDTOOut.class);
    userDto.setType("Bearer");
    userDto.setToken(getJWTToken(user.getId()));
    userDto.setRefreshToken(NanoIdUtils.randomNanoId());
    return user.validate("firstname", user.getFirstname(), (name) -> this.userWriteRepository.exists(name))
    .then(this.userWriteRepository.add(user))
    .flatMap(entity -> {
        logger.info(this.serializeObject(entity, "added")); 
        return userWriteRepository.add(user).then(Mono.just(userDto));
    });
}
    
    @Override
    public Mono<UserDTOIn> get(UUID id) {
        return this.findById(id).flatMap( dbuser -> Mono.just(this.modelMapper.map(dbuser, UserDTOIn.class)));
    }

    @Override
    public Mono<UserDTOOut> update(UUID id, UpdateDTO updateDTO) {
        return this.findById(id).flatMap( dbUser -> {
            if(dbUser.getFirstname().equals(updateDTO.getFirstname())){
                User userUpdated = this.modelMapper.map(updateDTO, User.class);
                userUpdated.setId(dbUser.getId());
                userUpdated.setEmail(dbUser.getEmail());
                userUpdated.setRol(dbUser.getRol());
                userUpdated.validate();
                if(BCrypt.checkpw(updateDTO.getNewPassword(), dbUser.getPassword()) && BCrypt.checkpw(userUpdated.getPassword(), dbUser.getPassword())) {
                    userUpdated.setPassword(dbUser.getPassword());
                } else {
                    this.modelMapper.map(updateDTO, dbUser);
                    return dbUser.validate("name", dbUser.getFirstname(), (name) -> this.userWriteRepository.exists(name))
                    .then(this.userWriteRepository.update(dbUser))
                    .flatMap(user -> {
                        logger.info(this.serializeObject(dbUser, "updated")); 
                        return Mono.just(this.modelMapper.map(user, UserDTOOut.class));
                    });
                }  
            
            }
            User userUpdated = this.modelMapper.map(updateDTO, User.class);
            userUpdated.validate();
            return userUpdated.validate("firstname", userUpdated.getFirstname(), (name) -> this.userWriteRepository.exists(name))
        .then(this.userWriteRepository.update(dbUser)).flatMap(user -> 
            Mono.just(this.modelMapper.map(user, UserDTOOut.class)));               
   
        });
        
    } 

/*

    @Override
    public Mono<IngredientDTOOut> update(UUID id, IngredientDTOIn ingredientDTOIn) {
        return this.findById(id).flatMap( dbIngredient -> {
            if(dbIngredient.getName().equals(ingredientDTOIn.getName())){
                this.modelMapper.map(ingredientDTOIn, dbIngredient);
                dbIngredient.validate();
                return this.ingredientWriteRepository.update(dbIngredient).flatMap(ingredient -> Mono.just(this.modelMapper.map(ingredient, IngredientDTOOut.class)));
            } else{
                this.modelMapper.map(ingredientDTOIn, dbIngredient);
                return dbIngredient.validate("name", dbIngredient.getName(), (name) -> this.ingredientWriteRepository.exists(name))
                .then(this.ingredientWriteRepository.update(dbIngredient))
                .flatMap(ingredient -> {
                    logger.info(this.serializeObject(dbIngredient, "updated")); 
                    return Mono.just(this.modelMapper.map(ingredient, IngredientDTOOut.class));
                });
            }   
        });
    } 

*/






    @Override
    public Flux<UserProjection> getAll(String firstname, int page, int size) {
        return this.userReadRepository.getAll(firstname, page, size);
    }
    private String getJWTToken(UUID id) {
		String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("USER");
		String token = Jwts
				.builder()
				.setId(id.toString())
                .claim("authorities",
                    grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                     .collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
		return token;
	}
}


