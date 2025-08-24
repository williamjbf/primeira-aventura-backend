package github.williamjbf.primeiraaventura.user.service;

import github.williamjbf.primeiraaventura.table.dto.TableListItemDTO;
import github.williamjbf.primeiraaventura.table.service.TableService;
import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.service.TableSubscriptionService;
import github.williamjbf.primeiraaventura.user.dto.AllSubscriptionsUser;
import github.williamjbf.primeiraaventura.user.dto.UserRequestDTO;
import github.williamjbf.primeiraaventura.user.dto.UserResponse;
import github.williamjbf.primeiraaventura.user.exception.EmailNotFoundException;
import github.williamjbf.primeiraaventura.user.exception.UserAlreadyExistsException;
import github.williamjbf.primeiraaventura.user.exception.UserNotFoundException;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TableSubscriptionService tableSubscriptionService;
    private final TableService tableService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Lazy TableSubscriptionService tableSubscriptionService, @Lazy TableService tableService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tableSubscriptionService = tableSubscriptionService;
        this.tableService = tableService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // senha já criptografada
                .roles("USER")
                .build();
    }

    public User createUser(UserRequestDTO request) {
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByUsername(request.getUsername())) {
            errors.put("username", "Username já está em uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            errors.put("email", "Email já está em uso");
        }

        if (!errors.isEmpty()) {
            throw new UserAlreadyExistsException(errors);
        }

        User user = request.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + username));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email do usuário ão encontrado: " + email));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Id do usuário não encontrado: " + id));
    }

    public UserResponse getUser (String username){
        User user = findByUsername(username);

        List<TableListItemDTO> accepted = tableSubscriptionService.listarMesasInscritasPorStatus(user.getId(), SubscriptionStatus.ACEITO);
        List<TableListItemDTO> pending = tableSubscriptionService.listarMesasInscritasPorStatus(user.getId(), SubscriptionStatus.PENDENTE);
        List<TableListItemDTO> denied = tableSubscriptionService.listarMesasInscritasPorStatus(user.getId(), SubscriptionStatus.RECUSADO);

        List<TableListItemDTO> owner = tableService.listarMesasPorNarrador(user.getId());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .subscriptions(new AllSubscriptionsUser(accepted, pending, denied))
                .ownedTables(owner)
                .build();
    }

}
