package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.ProductBean;
import model.ProductService;
import model.spring.PrimitiveNumberEditor;

@Controller
public class ProductController {
	@InitBinder
	public void initializer(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		webDataBinder.registerCustomEditor(int.class,
				new PrimitiveNumberEditor(java.lang.Integer.class, true));
		
		webDataBinder.registerCustomEditor(double.class,
				new PrimitiveNumberEditor(java.lang.Double.class, true));
	}
	
	@Autowired
	private ProductService productService = null;
	
	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(
			path={"/pages/product.controller"}
	)
	public String method(String prodaction, ProductBean bean, BindingResult bindingResult, Model model,
			@RequestParam("id")	String temp1) {
		
		Locale locale = LocaleContextHolder.getLocale();
//接收資料
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);
		
//轉換資料
		if(bindingResult!=null && bindingResult.hasErrors()) {
			if(bindingResult.getFieldErrorCount("id")!=0) {
				errors.put("xxx1", context.getMessage("product.id.format", null, locale));
			}
			if(bindingResult.getFieldErrorCount("price")!=0) {
				errors.put("xxx2", context.getMessage("product.price.format", null, locale));
			}
			if(bindingResult.getFieldErrorCount("make")!=0) {
				errors.put("xxx3", context.getMessage("product.make.format", null, locale));
			}
			if(bindingResult.getFieldErrorCount("expire")!=0) {
				errors.put("xxx4", context.getMessage("product.expire.format", null, locale));
			}
		}
		
//驗證資料
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(temp1==null || temp1.length()==0) {
				errors.put("xxx1", context.getMessage("product.id.required", new String[] {prodaction}, locale));
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.error";
		}
		
//呼叫model
//根據model執行結果呼叫view元件
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.select";
			
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert fail");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.error";
			
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update fail");
			} else {
				model.addAttribute("update", result);
			}
			return "product.error";
			
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(!result) {
				model.addAttribute("delete", 0);
			} else {
				model.addAttribute("delete", 1);
			}
			return "product.error";			
		} else {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.error";
		}
	}
}
