package giovannilongo.U5S3L1220124.services;

import giovannilongo.U5S3L1220124.entities.Device;
import giovannilongo.U5S3L1220124.entities.User;
import giovannilongo.U5S3L1220124.exceptions.NotFoundException;
import giovannilongo.U5S3L1220124.payloads.NewDeviceDTO;
import giovannilongo.U5S3L1220124.repositories.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevicesService {
    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private UsersService usersService;

    public Device save(NewDeviceDTO body) {
        User user = usersService.findById(body.user_Id());
        Device newDevice = new Device();
        newDevice.setType(body.type());
        newDevice.setStatus(body.status());
        newDevice.setUser(user);
        return devicesRepository.save(newDevice);
    }

    public List<Device> getDevice() {
        return devicesRepository.findAll();
    }

    public Device findById(long id) {
        return devicesRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Device found = this.findById(id);
        devicesRepository.delete(found);
    }

    public Device findByIdAndUpdate(long id, NewDeviceDTO body) {
        Device found = this.findById(id);
        found.setType(body.type());
        found.setStatus(body.status());
        if (found.getUser().getId() != body.user_Id()) {
            User newUser = usersService.findById(body.user_Id());
            found.setUser(newUser);
        }
        return devicesRepository.save(found);
    }

    public List<Device> findByUser(long user_id) {
        User user = usersService.findById(user_id);
        return devicesRepository.findByUser(user);
    }

//    public Device addDeviceToUser(long userId, long deviceId) {
//        User user = usersService.findById(userId);
//        Device found = this.findById(deviceId);
//        if (found.getUser() != null) {
//            throw new BadRequestException("Il dispositivo è già assegnato a un altro utente");
//        }
//
//        user.addDeviceById(found);
//        usersService.save(user);
//        return found;
//    }
}
