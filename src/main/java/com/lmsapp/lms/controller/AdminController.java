package com.lmsapp.lms.controller;

import com.lmsapp.lms.dto.MaterialDto;
import com.lmsapp.lms.dto.NewsDto;
import com.lmsapp.lms.model.Enquiry;
import com.lmsapp.lms.model.Material;
import com.lmsapp.lms.model.News;
import com.lmsapp.lms.model.Response;
import com.lmsapp.lms.model.StudentInfo;
import com.lmsapp.lms.service.EnquiryRepo;
import com.lmsapp.lms.service.MaterialRepo;
import com.lmsapp.lms.service.NewsRepo;
import com.lmsapp.lms.service.ResponseRepo;
import com.lmsapp.lms.service.StudentRepo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
@RequestMapping({"/admin"})
public class AdminController {
   @Autowired
   StudentRepo srepo;
   @Autowired
   EnquiryRepo erepo;
   @Autowired
   ResponseRepo resrepo;
   @Autowired
   MaterialRepo mrepo;
   @Autowired
   NewsRepo nrepo;

   @GetMapping({"/adhome"})
   public String ShowAdminHome(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            long stdcount = srepo.count();
            model.addAttribute("stdcount", stdcount);
            model.addAttribute("enqcount", erepo.count());
            model.addAttribute("rescount", resrepo.count());
            model.addAttribute("matcount", mrepo.count());
            List<News> news = this.nrepo.findAll();
            Collections.reverse(news);
            model.addAttribute("news", news);
            model.addAttribute("dto", new NewsDto());
            return "admin/adhome";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @PostMapping({"/adhome"})
   public String AskQuestion(HttpSession session, HttpServletResponse response, @ModelAttribute NewsDto dto, BindingResult result, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            News news = new News();
            news.setNewstext(dto.getNewstext());
            String formattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            news.setNewsdate(formattedDate);
            this.nrepo.save(news);
            attrib.addFlashAttribute("message", "Form Submitted Succesfully");
            return "redirect:/admin/adhome";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/adhome/delete"})
   public String DeleteNews(@RequestParam int nid, HttpSession session, HttpServletResponse response, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            News news = (News)this.nrepo.findById(nid).get();
            this.nrepo.delete(news);
            return "redirect:/admin/adhome";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewstudent"})
   public String Showviewstudent(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            List<StudentInfo> stu = this.srepo.findAll();
            model.addAttribute("stu", stu);
            return "admin/viewstudent";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewenquiry"})
   public String Showviewenquiry(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            List<Enquiry> enq = this.erepo.findAll();
            Collections.reverse(enq);
            model.addAttribute("enquiry", enq);
            return "admin/viewenquiry";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewenquiry/delete"})
   public String Deleteenquiry(@RequestParam int id, HttpSession session, HttpServletResponse response, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            Enquiry eq = (Enquiry)this.erepo.findById(id).get();
            this.erepo.delete(eq);
            attrib.addFlashAttribute("msg", id + "is deleted succesfully");
            return "redirect:/admin/viewenquiry";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewfeedback"})
   public String Showviewfeedback(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            List<Response> res = this.resrepo.findResponseByResponseType("Feedback");
            Collections.reverse(res);
            model.addAttribute("feedback", res);
            return "admin/viewfeedback";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewfeedback/delete"})
   public String DeleteFeedback(@RequestParam int id, HttpSession session, HttpServletResponse response, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            Response res = (Response)this.resrepo.findById(id).get();
            this.resrepo.delete(res);
            attrib.addFlashAttribute("msg", id + "is deleted succesfully");
            return "redirect:/admin/viewfeedback";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewcomplain"})
   public String Showviewcomplain(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            List<Response> res = this.resrepo.findResponseByResponseType("Complaint");
            Collections.reverse(res);
            model.addAttribute("complain", res);
            return "admin/viewcomplain";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewcomplain/delete"})
   public String Deletecomplain(@RequestParam int id, HttpSession session, HttpServletResponse response, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            Response res = (Response)this.resrepo.findById(id).get();
            this.resrepo.delete(res);
            attrib.addFlashAttribute("msg", id + "is deleted succesfully");
            return "redirect:/admin/viewcomplain";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/addstudymaterial"})
   public String Showaddstudymaterial(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            MaterialDto dto = new MaterialDto();
            model.addAttribute("dto", dto);
            return "admin/addstudymaterial";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @PostMapping({"/addstudymaterial"})
   public String CreateStudyMaterial(HttpSession session, HttpServletResponse response, @ModelAttribute MaterialDto dto, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") == null) {
            return "redirect:/adminlogin";
         } else {
            MultipartFile filedata = dto.getFiledata();
            long date = (new Date()).getTime();
            String storageFileName = date + "_" + filedata.getOriginalFilename();
            String uploadDir = "public/mat/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
               Files.createDirectories(uploadPath);
            }
            try(InputStream inputstream = filedata.getInputStream()) {
					Files.copy(inputstream,Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);	
				}

            Material m = new Material();
            m.setProgram(dto.getProgram());
            m.setBranch(dto.getBranch());
            m.setYear(dto.getYear());
            m.setMaterialtype(dto.getMaterialtype());
            m.setSubject(dto.getSubject());
            m.setTopic(dto.getTopic());
            m.setFilename(storageFileName);
            m.setPosteddate(new Date() + "");
            this.mrepo.save(m);
            attrib.addFlashAttribute("msg", "material is added");
            return "redirect:/admin/addstudymaterial";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewstudymaterial"})
   public String Showviewstudymaterial(HttpSession session, HttpServletResponse response, Model model) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            List<Material> mlist = this.mrepo.findAll();
            Collections.reverse(mlist);
            model.addAttribute("mlist", mlist);
            return "admin/viewstudymaterial";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/viewstudymaterial/deletematerial"})
   public String DeleteMaterial(@RequestParam int id, HttpSession session, HttpServletResponse response, RedirectAttributes attrib) {
      try {
         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         if (session.getAttribute("adminid") != null) {
            Material m = (Material)this.mrepo.getById(id);
            Path filepath = Paths.get("public/mat/" + m.getFilename());

            try {
               Files.delete(filepath);
            } catch (Exception e) {
               e.printStackTrace();
            }

            this.mrepo.delete(m);
            attrib.addFlashAttribute("msg", id + "is deleted succesfully");
            return "redirect:/admin/viewstudymaterial";
         } else {
            return "redirect:/adminlogin";
         }
      } catch (Exception e) {
         return "redirect:/adminlogin";
      }
   }

   @GetMapping({"/logout"})
   public String Logout(HttpSession session) {
      session.invalidate();
      return "redirect:/adminlogin";
   }
}
