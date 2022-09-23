package com.libre.updown.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.libre.updown.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Path raiz = Paths.get("uploadsAndDownloadP"); 
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			Files.createDirectory(raiz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("No se puede crear la carpeta: " + e.getMessage());
		}
	}

	@Override
	public void save(MultipartFile file) {
		// TODO Auto-generated method stub
		try {
			Files.copy(file.getInputStream(), this.raiz.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error al guardar el archivo: "  + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		// TODO Auto-generated method stub
		
		try {
			Path file = raiz.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Error al leer el archivo");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(raiz.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		try {
			return Files.walk(this.raiz, 1).filter(path -> !path.equals(this.raiz)).map(this.raiz::relativize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("No se pueden cargar los archivos: " + e.getMessage());
		}
	}
	
}
