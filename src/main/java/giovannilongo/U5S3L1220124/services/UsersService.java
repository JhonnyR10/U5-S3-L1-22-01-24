package giovannilongo.U5S3L1220124.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giovannilongo.U5S3L1220124.entities.User;
import giovannilongo.U5S3L1220124.exceptions.BadRequestException;
import giovannilongo.U5S3L1220124.exceptions.NotFoundException;
import giovannilongo.U5S3L1220124.payloads.NewUserDTO;
import giovannilongo.U5S3L1220124.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public User save(NewUserDTO body) throws IOException {
        usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.email() + " è già stata utilizzata");
        });
        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setAvatar("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());
        newUser.setFirstName(body.firstName());
        newUser.setEmail(body.email());
        newUser.setPassword(body.password());
        newUser.setLastName(body.lastName());

        return usersRepository.save(newUser);
    }

    public Page<User> getUser(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return usersRepository.findAll(pageable);
    }

    public User findById(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        User found = this.findById(id);
        usersRepository.delete(found);
    }

    public User findByIdAndUpdate(long id, User body) {

        User found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setFirstName(body.getFirstName());
        found.setLastName(body.getLastName());
        found.setAvatar(body.getAvatar());
        return usersRepository.save(found);
    }

    public User uploadAvatar(long id, MultipartFile file) throws IOException {
        User found = this.findById(id);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatarURL);
        return usersRepository.save(found);
    }

    public User findByEmail(String email) throws NotFoundException {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
    }
}
