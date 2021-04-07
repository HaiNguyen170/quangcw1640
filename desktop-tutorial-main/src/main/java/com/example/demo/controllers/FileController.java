package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.models.*;
import com.example.demo.repository.*;
import com.example.demo.payload.response.*;

import java.io.IOException;
import java.util.stream.Stream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.util.StringUtils;
//gaga
/*
 * 
 *  @Autowired
  private FileDBRepository fileDBRepository;

  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }*/

@Controller
@CrossOrigin("*")
@RequestMapping("/api/submit")
public class FileController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	FileRepository fileRepository;

	public File store(MultipartFile file,long user_id,long article_id) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File FileDB = new File(fileName, file.getContentType(), file.getBytes(),false);
		User user = userRepository.findById(user_id)
				.orElseThrow(()-> new RuntimeException("Can not found user"));
		Article article = articleRepository.findById(article_id)
				.orElseThrow(()-> new RuntimeException("Can not found article"));
		FileDB.setUser(user);
		FileDB.setArticle(article);
		return fileRepository.save(FileDB);
	}
	
	private void sendemail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("khoitvhe130007@gmail.com", "Kh0itran99");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("khoitvhe130007@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("khoitvhe130007@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("C:\\Users\\khoit\\Downloads\\Home screen-pana (1).png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		}
	@PostMapping(value = "/sendemail")
	public ResponseEntity<?> sendEmail() throws AddressException, MessagingException, IOException {
		//TODO: process POST request
		sendemail();
		return ResponseEntity.ok(new MessageResponse("Send email successfully!"));
	}


	@PostMapping(value = "/upload/{user_id}/{article_id}")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("user_id") long user_id
			,@PathVariable("article_id") long article_id) {
		// TODO: process POST request
		String message = "";
		try {
			store(file,user_id,article_id);
			message = "Uploaded the file successfully : " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}

	@GetMapping(value = "/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = fileRepository.findAll().stream().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/submit/files/")
					.path(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping(value = "/files/article_id/{article_id}")
	public ResponseEntity<List<ResponseFile>> getListFileByArticle(@PathVariable("article_id") long article_id) {
		List<ResponseFile> files = fileRepository.findByArticle_id(article_id).stream().map(dbFile->{
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/submit/files/")
					.path(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	
	@GetMapping(value = "/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		File file = fileRepository.findById(id).get();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.body(file.getData());
	}
	
	@GetMapping(value = "/files/published")
	public ResponseEntity<List<ResponseFile>> getListFileByPublished() {
		List<ResponseFile> files = fileRepository.findByPublished(true).stream()
				.map(dbFile ->{
					String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/submit/files/")
							.path(dbFile.getId()).toUriString();
					return new ResponseFile(dbFile.getName(), fileDownloadURI, dbFile.getType(), dbFile.getData().length);
				}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
	@GetMapping(value = "/files/creator/{id}")
	public ResponseEntity<List<ResponseFile>> getListFileByCreator(@PathVariable("id") long id) {
		List<ResponseFile> files = fileRepository.findByUser(id).stream()
				.map(dbFile ->{
					String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/submit/files/")
							.path(dbFile.getId()).toUriString();
					return new ResponseFile(dbFile.getName(), fileDownloadURI, dbFile.getType(), dbFile.getData().length);
				}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}


}
