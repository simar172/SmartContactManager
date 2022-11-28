package com.example.demo.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Upload {

	public String upload(MultipartFile mf, String uname, String type) throws IOException {
		File mainpath = new File("D:\\contactproj\\contacts\\src\\main\\resources\\static\\Images");

		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		if (!mainpath.exists()) {
			mainpath.mkdir();
		}
		File unamefolder = new File(mainpath.getAbsolutePath() + File.separator + uname);
		if (!unamefolder.exists()) {
			unamefolder.mkdir();
		}
		File typeImg = new File(unamefolder.getAbsolutePath() + File.separator + type);
		if (!typeImg.exists()) {
			typeImg.mkdir();
		}

		Files.copy(mf.getInputStream(), Path.of(typeImg + File.separator + mf.getOriginalFilename()),
				StandardCopyOption.REPLACE_EXISTING);
		return mf.getOriginalFilename();
	}

	public InputStream serveImage(String filename, String uname) throws FileNotFoundException {
		// TODO Auto-generated method stub
		InputStream isInputStream = new FileInputStream(
				"Images" + File.separator + uname + File.separator + "contact" + File.separator + filename);
		return isInputStream;
	}

}
