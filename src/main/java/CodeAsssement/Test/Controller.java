package CodeAsssement.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class Controller {

	private static String UPLOADED_FOLDER = System.getProperty("user.home");
;

	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping("/upload") 
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		try {
			File uploadedFiles = new File(UPLOADED_FOLDER);
			if (!uploadedFiles.exists()) {
				uploadedFiles.mkdirs();
			}
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER+"/" + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					" successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}


}
