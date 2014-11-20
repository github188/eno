package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Docinfo;
import com.energicube.eno.monitor.model.Doctypes;
import com.energicube.eno.monitor.repository.DocinfoRepository;
import com.energicube.eno.monitor.repository.DoctypesRepository;
import com.energicube.eno.monitor.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/pacs/attachment")
public class AttachmentController extends BaseController {

    private FileManagerService fileManagerService;

    @Autowired
    private DocinfoRepository docinfoRepository;

    @Autowired
    private DoctypesRepository doctypesRepository;

    @Autowired
    public AttachmentController(FileManagerService fileManagerService) {
        this.fileManagerService = fileManagerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initFileManager(Model model) {
        List<Doctypes> doctypes = doctypesRepository.findAll();
        model.addAttribute("doctypes", doctypes);
        model.addAttribute("bz", "0");
        return "attachment/fileManagerView";
    }

    @RequestMapping(value = "/showFile")
    public String showFile(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        List<Doctypes> doctypes = doctypesRepository.findAll();
        String doctypesid = request.getParameter("doctypesid");
        Doctypes doctype = doctypesRepository.findOne(Integer
                .valueOf(doctypesid));
        String document = "";
        String urlname = "";
        List<Docinfo> docinfos = new ArrayList<Docinfo>();
        List<Docinfo> docinfoList = docinfoRepository.findByDoctype(doctype
                .getDoctype());
        if (docinfoList.size() > 0) {
            for (int i = 0; i < docinfoList.size(); i++) {
                Docinfo docinfo = docinfoList.get(i);
                urlname = docinfo.getUrlname();
                String str[] = urlname.split("\\.");
                document = docinfo.getDocument() + "." + str[1];
                docinfo.setDocument(document);
                docinfos.add(docinfo);
            }
        }
        model.addAttribute("doctypes", doctypes);
        model.addAttribute("docinfos", docinfos);
        model.addAttribute("bz", "1");
        return "attachment/fileManagerView";
    }

    @RequestMapping(value = "/docinfos")
    public String showDocinfos(HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        List<Doctypes> doctypes = doctypesRepository.findAll();
        String doctype = request.getParameter("doctype");
        String document = "";
        String urlname = "";
        List<Docinfo> docinfos = new ArrayList<Docinfo>();
        List<Docinfo> docinfoList = docinfoRepository.findByDoctype(doctype);
        if (docinfoList.size() > 0) {
            for (int i = 0; i < docinfoList.size(); i++) {
                Docinfo docinfo = docinfoList.get(i);
                urlname = docinfo.getUrlname();
                String str[] = urlname.split("\\.");
                document = docinfo.getDocument() + "." + str[1];
                docinfo.setDocument(document);
                docinfos.add(docinfo);
            }
        }
        model.addAttribute("doctypes", doctypes);
        model.addAttribute("docinfos", docinfos);
        model.addAttribute("bz", "1");
        return "attachment/fileManagerView";
    }

    @RequestMapping(value = "/view")
    public String imageView(HttpServletRequest request,
                            HttpServletResponse response, Model model) {
        String docinfoid = request.getParameter("docinfoid");
        Docinfo docinfo = docinfoRepository.findOne(Long.valueOf(docinfoid));
        model.addAttribute("urlname", docinfo.getUrlname());
        return "attachment/imageView";
    }

    @RequestMapping(value = "/delete")
    public String deleteFile(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        String docinfoid = request.getParameter("docinfoid");
        Docinfo docinfo = docinfoRepository.findOne(Long.valueOf(docinfoid));
        String filePath = request.getSession().getServletContext()
                .getRealPath("");
        int pos = filePath.lastIndexOf("\\", filePath.length());
        pos = filePath.lastIndexOf("\\", pos);
        filePath = filePath.substring(0, pos + 1) + docinfo.getUrlname();
        File file = new File(filePath);
        file.delete();
        docinfoRepository.delete(Long.valueOf(docinfoid));
        return "redirect:fileManagerView";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        String docinfoid = request.getParameter("docinfoid");
        Docinfo docinfo = docinfoRepository.findOne(Long.valueOf(docinfoid));
        String filePath = request.getSession().getServletContext()
                .getRealPath("");
        int pos = filePath.lastIndexOf("\\", filePath.length());
        pos = filePath.lastIndexOf("\\", pos);
        filePath = filePath.substring(0, pos + 1) + docinfo.getUrlname();
        String fileName = "";
        try {
            if (filePath.lastIndexOf("/") > 0) {
                fileName = new String(filePath.substring(
                        filePath.lastIndexOf("/") + 1, filePath.length())
                        .getBytes("GB2312"), "ISO8859_1");
            } else if (filePath.lastIndexOf("\\") > 0) {
                fileName = new String(filePath.substring(
                        filePath.lastIndexOf("\\") + 1, filePath.length())
                        .getBytes("GB2312"), "ISO8859_1");
            }

        } catch (Exception e) {
        }
        // 打开指定文件的流信息
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        // 设置响应头和保存文件名
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + fileName + "\"");
        // 写出流信息
        int b = 0;
        try {
            PrintWriter out = response.getWriter();
            while ((b = fs.read()) != -1) {
                out.write(b);
            }
            fs.close();
            out.close();
            System.out.println("文件下载完毕.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载文件失败!");
        }
    }
}
