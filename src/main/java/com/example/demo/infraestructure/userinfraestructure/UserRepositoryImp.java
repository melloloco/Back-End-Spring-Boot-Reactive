package com.example.demo.infraestructure.userinfraestructure;
import java.util.UUID;
import com.example.demo.domain.userdomain.User;
import com.example.demo.domain.userdomain.UserProjection;
import com.example.demo.domain.userdomain.UserReadRepository;
import com.example.demo.domain.userdomain.UserWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImp implements UserWriteRepository, UserReadRepository{

    private final UserReactiveRepositoryImp userReactiveRepository;

    @Autowired
    public UserRepositoryImp(UserReactiveRepositoryImp userReactiveRepository){
        this.userReactiveRepository = userReactiveRepository;
    }

    @Override
    public Mono<User> add(User user) {
        return this.userReactiveRepository.save(user);
    }

    @Override
    public Mono<User> findById(UUID id) {
        return this.userReactiveRepository.findById(id);
    }

    @Override
    public Mono<User> update(User user) {
        return this.userReactiveRepository.save(user);
    }
    
    @Override
    public Mono<Void> delete(User user) {
        return this.userReactiveRepository.delete(user);
    }

    @Override
    public Flux<UserProjection> getAll(String firstname, int page, int size) {
        return this.userReactiveRepository.findByCriteria(firstname, size, page);
    }

    @Override
    public Mono<Integer> exists(String firstname) {
        return this.userReactiveRepository.existsByName(firstname);
    }
}
