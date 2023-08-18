package com.care.sekki.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.care.sekki.S3.S3UploadService;
import com.care.sekki.common.PageService;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private S3UploadService s3UploadService;

	public String loginProc(MemberDTO member) {
		if (member.getId() == null || member.getId().isEmpty()) {
			return "아이디를 입력하세요.";
		}

		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		MemberDTO result = memberMapper.loginProc(member.getId());
		if (result != null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();

			if (bpe.matches(member.getPw(), result.getPw())) {
				session.setAttribute("id", result.getId());
				session.setAttribute("userName", result.getUserName());
				session.setAttribute("address", result.getAddress());
				session.setAttribute("mobile", result.getMobile());
				session.setAttribute("profilePictureUrl", result.getProfilePictureUrl());
				session.setAttribute("email", result.getEmail());
				session.setAttribute("height", result.getHeight());
				session.setAttribute("weight", result.getWeight());
				return "로그인 성공";
			}
		}

		return "아이디/비밀번호를 확인 후 다시 시도하세요.";
	}

	
	
	
	
	private List<ReviewDTO> reviewList = new ArrayList<>();
	public void addReview(ReviewDTO reviewDTO) {
		reviewList.add(reviewDTO);
		
	}
   
	
    public List<ReviewDTO> getAllReviews() {
        return reviewList;
    }
    

	public MemberDTO userInfo(String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}

		String sessionId = (String) session.getAttribute("id");
		if (sessionId.equals(id) == false && sessionId.equals("admin") == false)
			return null;


		return memberMapper.loginProc(id);
	}

	
	public String updateProc(MemberDTO member, String confirm) {
		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (member.getPw().equals(confirm) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}

		if (member.getUserName() == null || member.getUserName().isEmpty()) {
			return "이름을 입력하세요.";
		}

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);

		int result = memberMapper.updateProc(member);
		if (result == 1)
			return "회원 정보 수정 완료";
		return "회원 정보 수정 실패";
	}

	public String deleteProc(String id, String pw, String confirmPw) {
		if (pw == null || pw.isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (pw.equals(confirmPw) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		MemberDTO member = memberMapper.loginProc(id);
		if (member != null && bpe.matches(pw, member.getPw())) {
			memberMapper.delete(id);
			return "회원 정보 삭제 완료";
		}
		return "비밀번호를 확인 후 다시 시도하세요.";
	}


	/* Edamam.api 코드 */

	private final String appId = "7d8f664a";
	private final String appKey = "a6f332294d947d0141a2d499a7ac1688";

	public List<String> getIngredients() {
		// Edamam API를 호출하여 재료 데이터를 가져옴
		List<String> ingredients = new ArrayList<>();
		try {
			// Edamam API 호출하는 로직
			String apiURL = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			// API 응답 데이터를 파싱하고 필요한 정보를 추출하여 ingredients에 추가
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// API 응답 데이터를 파싱하여 재료 목록을 추출
			JSONObject responseObject = new JSONObject(response.toString());
			JSONArray hits = responseObject.getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				JSONArray ingredientsArray = recipe.getJSONArray("ingredientLines");
				for (int j = 0; j < ingredientsArray.length(); j++) {
					ingredients.add(ingredientsArray.getString(j));
				}
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return ingredients;
	}

	public List<String> getSeasoning() {
		// Edamam API를 호출하여 양념 데이터를 가져옴
		List<String> seasoning = new ArrayList<>();
		try {
			// Edamam API 호출하는 로직
			String apiURL = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			// API 응답 데이터를 파싱하고 필요한 정보를 추출하여 seasoning에 추가
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// API 응답 데이터를 파싱하여 양념 목록을 추출
			JSONObject responseObject = new JSONObject(response.toString());
			JSONArray hits = responseObject.getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				JSONArray dietLabelsArray = recipe.getJSONArray("dietLabels");
				for (int j = 0; j < dietLabelsArray.length(); j++) {
					seasoning.add(dietLabelsArray.getString(j));
				}
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return seasoning;
	}

	public JSONObject getRecipeData() {
		String url = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;

		try {
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();

			// API 응답 데이터를 JSON 형태로 파싱
			JSONObject responseObject = new JSONObject(responseBody);

			// 필요한 데이터 추출
			JSONArray hits = responseObject.getJSONArray("hits");
			List<String> recipeDataList = new ArrayList<>();
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				String recipeData = recipe.getString("recipeData");
				recipeDataList.add(recipeData);
			}

			// 추출한 데이터를 JSON 배열로 변환하여 반환
			return new JSONObject().put("recipeDataList", new JSONArray(recipeDataList));

		} catch (IOException | InterruptedException | JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject(); // 예외 발생 시 빈 JSONObject 반환
	}



}
