package com.exemplo.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	
	@GetMapping("/lista-vinhos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("/vinhos/lista-vinhos");
		modelAndView.addObject("vinhos", vinhos.findAll());
		return modelAndView;
	}
	//Método editar, funcionava na versão anterior, porém na nova não funciona
	//está criando um novo item na lista.
	/*
	 @GetMapping("/{id}")
	 public ModelAndView editar(@PathVariable("id") Long id){
	 return novo(vinhos.findOne(id);
	 } 
	 */
	
	//Novo método da versão nova (muda o retorno), porém continua cadastrando outro
	//item ao invés de editar.
	//
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		return novo(vinhos.findById(id).orElse(null));
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinhos/cadastro-vinho");
		
		modelAndView.addObject(vinho);
		modelAndView.addObject("tipos", TipoVinho.values());
		
		return modelAndView ;
	}
	
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
