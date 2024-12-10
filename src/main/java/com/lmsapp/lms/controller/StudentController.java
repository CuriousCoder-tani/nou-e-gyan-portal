package com.lmsapp.lms.controller;

import com.lmsapp.lms.dto.AnswerDto;
import com.lmsapp.lms.dto.QuestionDto;
import com.lmsapp.lms.dto.ResponseDto;
import com.lmsapp.lms.dto.StudentDto;
import com.lmsapp.lms.model.Answer;
import com.lmsapp.lms.model.Material;
import com.lmsapp.lms.model.News;
import com.lmsapp.lms.model.Question;
import com.lmsapp.lms.model.Response;
import com.lmsapp.lms.model.StudentInfo;
import com.lmsapp.lms.service.AnswerRepo;
import com.lmsapp.lms.service.MaterialRepo;
import com.lmsapp.lms.service.NewsRepo;
import com.lmsapp.lms.service.QuestionRepo;
import com.lmsapp.lms.service.ResponseRepo;
import com.lmsapp.lms.service.StudentRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/student"})
public class StudentController {
   @Autowired
   StudentRepo sturepo;
   @Autowired
   ResponseRepo resrepo;
   @Autowired
   MaterialRepo mrepo;
   @Autowired
   QuestionRepo qrepo;
   @Autowired
   AnswerRepo arepo;
   @Autowired
   NewsRepo nrepo;

   @GetMapping({"/stdhome"})
   public String showStudentHome(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo s = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            model.addAttribute("sinfo", s);
            StudentDto dto = new StudentDto();
            model.addAttribute("dto", dto);
            List<News> news = this.nrepo.findAll();
            model.addAttribute("news", news);
            String program = s.getProgram();
            String branch = s.getBranch();
            String year = s.getYear();
            List<Material> alist = this.mrepo.getMaterial(program, branch, year, "assign");
            model.addAttribute("acount", alist.size());
            List<Material> slist = this.mrepo.getMaterial(program, branch, year, "smat");
            model.addAttribute("studycount", slist.size());
            return "student/studenthome";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception var12) {
         return "redirect:/studentlogin";
      }
   }

   @PostMapping({"/stdhome"})
   public String uploadpic(HttpSession session, HttpServletResponse response, RedirectAttributes attrib, @ModelAttribute StudentDto dto) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") == null) {
            return "redirect:/studentlogin";
         } else {
            StudentInfo stu = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            MultipartFile filedata = dto.getProfilepic();
            String storageFileName = filedata.getOriginalFilename();
            String uploadDir = "public/user/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
               Files.createDirectories(uploadPath);
            }
            try(InputStream inputstream = filedata.getInputStream()) {
					Files.copy(inputstream,Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);	
				}

            stu.setProfilepic(storageFileName);
            this.sturepo.save(stu);
            attrib.addFlashAttribute("msg", "Form Submitted Succesfully");
            return "redirect:/student/stdhome";
         }
      } catch (Exception e) {
         attrib.addFlashAttribute("msg", "Something went wrong" + e.getMessage());
         return "redirect:/student/stdhome";
      }
   }

   @GetMapping({"/askquestion"})
   public String ShowAskQuestion(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            QuestionDto dto = new QuestionDto();
            model.addAttribute("dto", dto);
            return "student/askquestion";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @PostMapping({"/askquestion"})
   public String AskQuestion(HttpSession session, HttpServletResponse response, @ModelAttribute QuestionDto dto, BindingResult result, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo s = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            Question q = new Question();
            q.setQuestion(dto.getQuestion());
            q.setPostedby(s.getName());
            q.setPosteddate(new Date() + "");
            this.qrepo.save(q);
            attrib.addFlashAttribute("message", "Form Submitted Succesfully");
            return "redirect:/student/askquestion";
         } else {
            return "redirect:/student/askquestion";
         }
      } catch (Exception e) {
         attrib.addFlashAttribute("message", "Something went wrong");
         return "redirect:/student/askquestion";
      }
   }

   @GetMapping({"/showanswer"})
   public String ShowAnswer(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            model.addAttribute("dto", new AnswerDto());
            List<Question> questions = this.qrepo.findAllByOrderByPosteddateDesc();
            List<Answer> answers = this.arepo.findAllByOrderByPosteddateDesc();
            model.addAttribute("questions", questions);
            model.addAttribute("answers", answers);
            return "student/giveanswer";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @PostMapping({"/showanswer"})
   public String giveAnswer(@RequestParam int qid, HttpSession session, HttpServletResponse response, @ModelAttribute AnswerDto dto, BindingResult result, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo student = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            Answer answer = new Answer();
            answer.setQuestion(dto.getQid());
            answer.setAnswer(dto.getAnswer());
            answer.setAnsweredby(student.getName());
            String formattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            answer.setPosteddate(formattedDate);
            this.arepo.save(answer);
            return "redirect:/student/showanswer";
         } else {
            return "redirect:/student/showanswer";
         }
      } catch (Exception e) {
         return "redirect:/student/showanswer";
      }
   }

   @GetMapping({"/viewassignment"})
   public String ShowViewAssignment(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo s = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            String program = s.getProgram();
            String branch = s.getBranch();
            String year = s.getYear();
            String materialtype = "assign";
            List<Material> mlist = this.mrepo.getMaterial(program, branch, year, materialtype);
            model.addAttribute("mlist", mlist);
            return "student/viewassignment";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @GetMapping({"/viewstudymaterial"})
   public String ShowStudyMaterial(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo s = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            String program = s.getProgram();
            String branch = s.getBranch();
            String year = s.getYear();
            String materialtype = "smat";
            List<Material> mlist = this.mrepo.getMaterial(program, branch, year, materialtype);
            model.addAttribute("mlist", mlist);
            return "student/viewstudymaterial";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @GetMapping({"/giveresponse"})
   public String ShowGiveResponse(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            ResponseDto dto = new ResponseDto();
            model.addAttribute("dto", dto);
            return "student/response";
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @PostMapping({"/giveresponse"})
   public String GiveResponse(HttpSession session, HttpServletResponse response, @ModelAttribute ResponseDto responseDto, BindingResult result, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            Response res = new Response();
            StudentInfo stu = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            res.setRollno(stu.getRollno());
            res.setBranch(stu.getBranch());
            res.setContactno(stu.getContactno());
            res.setEmailaddress(stu.getEmailaddress());
            res.setName(stu.getName());
            res.setProgram(stu.getProgram());
            res.setYear(stu.getYear());
            res.setResponsetype(responseDto.getResponsetype());
            res.setResponsesubject(responseDto.getResponsesubject());
            res.setResponsetext(responseDto.getResponsetext());
            res.setResponsedate(new Date() +"");
            this.resrepo.save(res);
            attrib.addFlashAttribute("message", "Form Submitted Succesfully");
            return "redirect:/student/giveresponse";
         } else {
            return "redirect:/student/giveresponse";
         }
      } catch (Exception e) {
         attrib.addFlashAttribute("message", "Something went wrong");
         return "redirect:/student/giveresponse";
      }
   }

   @GetMapping({"/changepassword"})
   public String ShowChangePassword(HttpSession session, HttpServletResponse response) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         return session.getAttribute("studentid") != null ? "student/changepassword" : "redirect:/studentlogin";
      } catch (Exception e) {
         return "redirect:/studentlogin";
      }
   }

   @PostMapping({"/changepassword"})
   public String ChangePassword(HttpSession session, HttpServletResponse response, HttpServletRequest request, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("studentid") != null) {
            StudentInfo stu = (StudentInfo)this.sturepo.getById((Integer)session.getAttribute("studentid"));
            String oldpassword = request.getParameter("oldpassword");
            String newpassword = request.getParameter("newpassword");
            String confirmpassword = request.getParameter("confirmpassword");
            if (!newpassword.equals(confirmpassword)) {
               attrib.addFlashAttribute("message", "New password and confirmpassword are not same");
               return "redirect:/student/changepassword";
            } else if (!oldpassword.equals(stu.getPassword())) {
               attrib.addFlashAttribute("message", "Old password is not correct");
               return "redirect:/student/changepassword";
            } else {
               stu.setPassword(newpassword);
               this.sturepo.save(stu);
               return "redirect:/student/logout";
            }
         } else {
            return "redirect:/studentlogin";
         }
      } catch (Exception e) {
         return "redirect:/student";
      }
   }

   @GetMapping({"/logout"})
   public String Logout(HttpSession session) {
      session.invalidate();
      return "redirect:/studentlogin";
   }
}
