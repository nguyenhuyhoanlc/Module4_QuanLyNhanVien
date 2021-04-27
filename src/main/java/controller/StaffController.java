package controller;

import model.Staff;
import model.StaffForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.IStaffService;
import service.StaffServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller

public class StaffController {
    @Value("${file-upload}")
    private String fileUpload;
    private IStaffService staffService = new StaffServiceImpl();

    @RequestMapping("")
    public String index(Model model) {
        List<Staff> staffs = staffService.findAll();
        model.addAttribute("staff", staffs);
        return "/showAll";
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("staffForm", new StaffForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveStaff(@ModelAttribute StaffForm staffForm) {
        MultipartFile multipartFile = staffForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(staffForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Staff staff = new Staff(staffForm.getId(), staffForm.getName(),
                staffForm.getDate(), fileName,staffForm.isGender());
        staffService.save(staff);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("staffForm", staffForm);
        modelAndView.addObject("message", "Created new staff successfully !");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Staff st = staffService.findById(id);
//        StaffForm staffForm = staffService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("staff1", st);
        return modelAndView;
    }

    @PostMapping("/editStaff")
    public String editStaff(@ModelAttribute StaffForm staffForm, Model model) {
        if ( staffForm.getName().trim().equals("") && staffForm.getDate().trim().equals("")) {
            model.addAttribute("status", "invalid  try again");
            return "/edit";
        }
        System.out.println("name" + staffForm.getName());
        MultipartFile multipartFile = staffForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(staffForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Staff staff1 = new Staff(staffForm.getId(), staffForm.getName(),
                staffForm.getDate(),fileName,staffForm.isGender());
        staffService.update(staff1);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        model.addAttribute("ab", staffService.findById(id));
        System.out.println(staffService.findById(id).getName());
        return "/detele";
    }

    @PostMapping("/delete1")
    public String deteleStaff(@ModelAttribute("ab") Staff staff){
        System.out.println(staff.getName());

        staffService.remove(staff.getId());
        return "redirect:/";
    }
}
