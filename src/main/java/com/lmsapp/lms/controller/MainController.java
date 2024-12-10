package com.lmsapp.lms.controller;
 
import com.lmsapp.lms.api.SmsSender;
import com.lmsapp.lms.dto.AdminLoginDto;
import com.lmsapp.lms.dto.EnquiryDto;
import com.lmsapp.lms.dto.StudentDto;
import com.lmsapp.lms.model.AdminLogin;
import com.lmsapp.lms.model.Colleges;
import com.lmsapp.lms.model.Enquiry;
import com.lmsapp.lms.model.StudentInfo;
import com.lmsapp.lms.service.AdminLoginRepo;
import com.lmsapp.lms.service.CollegesRepo;
import com.lmsapp.lms.service.EnquiryRepo;
import com.lmsapp.lms.service.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
   @Autowired
   EnquiryRepo erepo;
   @Autowired
   StudentRepo sturepo;
   @Autowired
   AdminLoginRepo adrepo;
   @Autowired
   CollegesRepo crepo;

   @GetMapping({"/home"})
   public String ShowIndex() {
      return "index";
   }

   @GetMapping({"/course"})
   public String ShowCourse() {
      return "Course";
   }

   @GetMapping({"/services"})
   public String Showservices() {
      return "services";
   }

   @GetMapping({"/studycenter"})
   public String ShowStudyCenter(Model model) {
      List<Colleges> col = this.crepo.findAll();
      model.addAttribute("college", col);
      return "studycenter";
   }

   @GetMapping({"/studentlogin"})
   public String ShowStudentLogin(Model model) {
      StudentDto dto = new StudentDto();
      model.addAttribute("dto", dto);
      return "studentlogin";
   }

   @PostMapping({"/studentlogin"})
   public String validateStudent(@ModelAttribute StudentDto dto, HttpSession session, RedirectAttributes attrib) {
      try {
         StudentInfo s = (StudentInfo)this.sturepo.getById(dto.getRollno());
         if (s.getPassword().equals(dto.getPassword())) {
            attrib.addFlashAttribute("msg", "Valid User");
            session.setAttribute("studentid", s.getRollno());
            return "redirect:/student/stdhome";
         } else {
            attrib.addFlashAttribute("msg", "Invalid User");
            return "redirect:/studentlogin";
         }
      } catch (EntityNotFoundException var5) {
         attrib.addFlashAttribute("msg", "Student does not exist");
         return "redirect:/studentlogin";
      }
   }

   @GetMapping({"/adminlogin"})
   public String ShowadminLogin(Model model) {
      AdminLoginDto dto = new AdminLoginDto();
      model.addAttribute("dto", dto);
      return "adminlogin";
   }

   @PostMapping({"/adminlogin"})
   public String adminlogin(@ModelAttribute AdminLoginDto dto, HttpSession session, RedirectAttributes attrib) {
      try {
         AdminLogin admin = (AdminLogin)this.adrepo.getById(dto.getAdminid());
         if (admin.getPassword().equals(dto.getPassword())) {
            attrib.addFlashAttribute("msg", "Valid Password");
            session.setAttribute("adminid", dto.getAdminid());
            return "redirect:/admin/adhome";
         } else {
            attrib.addFlashAttribute("msg", "Invalid Password");
            return "redirect:/adminlogin";
         }
      } catch (EntityNotFoundException ex) {
         attrib.addFlashAttribute("msg", "Admin does not exist");
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/registration"})
   public String Registration(Model model) {
      StudentDto dto = new StudentDto();
      model.addAttribute("dto", dto);
      return "registration";
   }

   @PostMapping({"/registration"})
   public String StudentRegistration(@ModelAttribute StudentDto studentDto, BindingResult result, RedirectAttributes redirectAttributes) {
      try {
         StudentInfo stu = new StudentInfo();
         stu.setRollno(studentDto.getRollno());
         stu.setName(studentDto.getName());
         stu.setAddress(studentDto.getAddress());
         stu.setBranch(studentDto.getBranch());
         stu.setContactno(studentDto.getContactno());
         stu.setEmailaddress(studentDto.getEmailaddress());
         stu.setGender(studentDto.getGender());
         stu.setPassword(studentDto.getPassword());
         stu.setProgram(studentDto.getProgram());
         stu.setYear(studentDto.getYear());
			stu.setRegdate(new Date()+ "");
         this.sturepo.save(stu);
         redirectAttributes.addFlashAttribute("message", "Form Submitted Succesfully");
         return "redirect:/registration";
      } catch (Exception e) {
         redirectAttributes.addFlashAttribute("message", "Something went Wrong");
         return "redirect:/registration";
      }
   }

   @GetMapping({"/contact"})
   public String ShowContact(Model model) {
      EnquiryDto dto = new EnquiryDto();
      model.addAttribute("dto", dto);
      return "contact";
   }

   @PostMapping({"/contact"})
   public String SubmitEnquiry(@ModelAttribute EnquiryDto enquiryDto, BindingResult result, RedirectAttributes redirectAttributes) {
      try {
         Enquiry eq = new Enquiry();
         eq.setName(enquiryDto.getName());
         eq.setGender(enquiryDto.getGender());
         eq.setContactno(enquiryDto.getContactno());
         eq.setEmailaddress(enquiryDto.getEmailaddress());
         eq.setEnquirytext(enquiryDto.getEnquirytext());
         eq.setEnquirydate(new Date() + "");
         this.erepo.save(eq);
         SmsSender sms = new SmsSender();
         sms.sendSms(enquiryDto.getContactno());
         redirectAttributes.addFlashAttribute("message", "Form Submitted Succesfully");
         return "redirect:/contact";
      } catch (Exception e) {
         redirectAttributes.addFlashAttribute("message", "Something went Wrong");
         return "redirect:/contact";
      }
   }
}
