package hu.davidhalma.minecraft.repository;

import hu.davidhalma.minecraft.db.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
