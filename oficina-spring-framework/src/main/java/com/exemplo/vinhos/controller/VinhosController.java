package com.exemplo.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exemplo.vinhos.model.TipoVinho;
import com.exemplo.vinhos.model.Vinho;
import com.exemplo.vinhos.repository.Vinhos;

//import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {
	
	@Autowired
	private Vinhos vinhos;
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		vinhos.deleteById(id);
		
		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");
		
		return "redirect:/vinhos/lista-vinhos";
	}
	
	
	//Método para a listagem de vinhos
	@GetMapping("/lista-vinhos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("/vinhos/lista-vinhos");
		modelAndView.addObject("vinhos", vinhos.findAll());
		return modelAndView;
	}
	
	
	//Método para edição dos itens
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		return novo(vinhos.findById(id).orElse(null));
	}
	
	
	//Método para adicionar um novo vinho
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinhos/cadastro-vinho");
		
		modelAndView.addObject(vinho);
		modelAndView.addObject("tipos", TipoVinho.values());
		
		return modelAndView ;
	}
	
	
	//Método para verficar se há erros e salvar um novo vinho
	@PostMapping("/novo")
 	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			//System.out.println(result);
			return novo(vinho);
		}
	vinhos.save(vinho);
	attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
	
	return new ModelAndView("redirect:/vinhos/novo");
}

}
