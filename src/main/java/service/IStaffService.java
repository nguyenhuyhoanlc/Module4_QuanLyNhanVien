package service;

import model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IStaffService {
    List<Staff> findAll();

    void save(Staff staff);

    Staff findById(long id);

    Staff update(Staff staff);

    void remove(long id);
}
