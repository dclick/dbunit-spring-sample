package io.redspark.dbunitspring.sample.domain;

import io.redspark.dbunitspring.sample.CustomDatasetLoader;
import io.redspark.dbunitspring.sample.domain.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

/**
 * @author vinicius.moreira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:context/root/config.xml")
@DbUnitConfiguration(dataSetLoader=CustomDatasetLoader.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
public class UsuarioTest {
	
	public static final String DATASET_CENARIO_LIMPO = "classpath:datasets/cenario_limpo.xml";
	
	public static final String DATASET_CENARIO_01 = "classpath:datasets/cenario_01.xml";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	@DatabaseSetup(DATASET_CENARIO_01)
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testFind$usuarioSemApelido(){
		Usuario usuario = this.entityManager.find(Usuario.class, 2L);
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApelido());
	}
	
	@Test
	@DatabaseSetup(DATASET_CENARIO_01)
	@DatabaseTearDown(DATASET_CENARIO_LIMPO)
	public void testPublicar$novoConteudo(){
		
		Usuario usuario = this.entityManager.find(Usuario.class, 1L);
		Assert.assertNotNull(usuario);
		Assert.assertThat(usuario.getApelido(), Matchers.is("Publicador 1"));
		Assert.assertThat(usuario.getEmail(), Matchers.is("novo@x.com"));
		Assert.assertNotNull(usuario.getPublicacoes());
		Assert.assertThat(usuario.getPublicacoes(), Matchers.hasSize(2));
		
		usuario.publicar("Minha Terceira Publicação");
		
		this.entityManager.merge(usuario);
		this.entityManager.flush();
		this.entityManager.clear();
		
		usuario = this.entityManager.find(Usuario.class, usuario.getId());
		Assert.assertThat(usuario.getPublicacoes(), Matchers.hasSize(3));
	}
	
}