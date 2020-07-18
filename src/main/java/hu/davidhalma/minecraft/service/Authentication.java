package hu.davidhalma.minecraft.service;

import hu.davidhalma.minecraft.exception.NotRegisteredUser;
import hu.davidhalma.minecraft.config.Messages;
import hu.davidhalma.minecraft.db.User;
import hu.davidhalma.minecraft.exception.UserAlreadyExists;
import hu.davidhalma.minecraft.exception.UserAlreadyLoggedIn;
import hu.davidhalma.minecraft.exception.WrongPassword;
import hu.davidhalma.minecraft.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.bukkit.entity.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Authentication {

  private final Set<String> onlinePlayers = new HashSet<>();



  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private final UserRepository userRepository;
  private final Messages messages;

  public Authentication(UserRepository userRepository, Messages messages) {
    this.userRepository = userRepository;
    this.messages = messages;
  }


  public boolean loggedIn(String id) {
    return onlinePlayers.contains(id);
  }

  public void register(Player player, String password)
      throws NotRegisteredUser, WrongPassword, UserAlreadyExists, UserAlreadyLoggedIn {
    if (userRepository.findById(player.getUniqueId().toString()).isPresent()) {
      throw new UserAlreadyExists(messages.getUserAlreadyExists());
    }
    if (loggedIn(player.getUniqueId().toString())) {
      throw new UserAlreadyLoggedIn(messages.getUserAlreadyLoggedIn());
    }

    User user = new User();
    user.setId(player.getUniqueId().toString());
    user.setName(player.getDisplayName());
    user.setPassword(passwordEncoder.encode(password));

    User save = userRepository.save(user);
    login(save.getId(), password);
  }

  public void login(String id, String password) throws NotRegisteredUser, WrongPassword, UserAlreadyLoggedIn {
    if (onlinePlayers.contains(id)){
      throw new UserAlreadyLoggedIn(messages.getUserAlreadyLoggedIn());
    }
    Optional<User> userOptional = userRepository.findById(id);
    User user = userOptional.orElseThrow(() -> new NotRegisteredUser(messages.getNotRegisteredUser()));
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new WrongPassword(messages.getWrongPassword());
    }
    onlinePlayers.add(id);
  }

  public void logout(String id) {
    onlinePlayers.remove(id);
  }

  public void passwordReset(String id, String oldPassword, String newPassword) throws NotRegisteredUser, WrongPassword {
    User user = userRepository.findById(id).orElseThrow(() -> new NotRegisteredUser("You are not registered."));

    if (!passwordEncoder.matches(oldPassword, user.getPassword())){
      throw new WrongPassword(messages.getWrongPassword());
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }
}
