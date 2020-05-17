package com.cachedemo.caching;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.cachedemo.caching.Cache;

@RestController
@SpringBootApplication
@RequestMapping(value = "/v1")
@Api(value = "Caching Service", description = "Caching Service with add and query operations.")
public class CachingService{
	
	@RequestMapping(value = "/cache", method = RequestMethod.POST, consumes = "text/plain", produces = "text/plain")
	@ApiOperation(value = "Add text data to the cache.")
	public ResponseEntity addToCache(@RequestBody String data){
		Cache cacheInstance = Cache.getInstance();
		int returnCode = cacheInstance.add(data);
		if(returnCode == 0){
			//new item added to the cache
			return new ResponseEntity<>("Data added to the cache", HttpStatus.CREATED);
		}
		else if(returnCode == 1){
			//item already present in the cache
			return new ResponseEntity<>("Data updated in the cache", HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>("Some error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/cache/{data}", method = RequestMethod.PUT, produces = "text/plain")
	@ApiOperation(value = "Query if text data exists in the cache or not.")
	public ResponseEntity queryFromCache(@PathVariable("data") String data){
		Cache cacheInstance = Cache.getInstance();
		boolean result;
		result = cacheInstance.query(data);
		if(result){
			return new ResponseEntity<>("data found in the cache!", HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>("data not found in the cache, adding it!", HttpStatus.CREATED);
		}
	}
	
	public static void main(String args[]){
		SpringApplication.run(CachingService.class, args);
	}
}