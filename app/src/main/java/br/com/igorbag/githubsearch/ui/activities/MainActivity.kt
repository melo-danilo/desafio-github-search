package br.com.igorbag.githubsearch.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.igorbag.githubsearch.api.model.response.Repository
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.extensions.getPreferenceData
import br.com.igorbag.githubsearch.extensions.showSnackBarRed
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import br.com.igorbag.githubsearch.viewModels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var context: Context
    private val viewModel: UserViewModel by viewModel()

    private lateinit var adapterRepository: RepositoryAdapter

    private lateinit var nomeUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        setupAdapter()
        setupListeners()
        showUserName()

    }

    private fun setupListeners() {
        binding.apply {

            btnConfirmar.setOnClickListener {
                saveUserLocal()
                getAllReposByUserName()
            }

        }
    }


    private fun saveUserLocal() {
        nomeUser = binding.etNomeUsuario.text.toString()
        if(nomeUser.isNotEmpty()){
            context.getPreferenceData().saveUser(nomeUser)
        }
    }

    private fun showUserName() {
        binding.etNomeUsuario.setText(context.getPreferenceData().getName())
    }

    private fun getAllReposByUserName() {

        setupObserver()

        viewModel.getRepositories(context.getPreferenceData().getName())

    }

    private fun setupObserver() {
        viewModel.repositories.observe(this) {
            adapterRepository.addAll(it)
        }

        viewModel.error.observe(this){
            binding.root.showSnackBarRed(it.toString())
        }
    }

    private fun setupAdapter() {
        adapterRepository = RepositoryAdapter(::openBrowser, ::shareRepositoryLink)
        binding.rvListaRepositories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRepository
        }
    }


    private fun shareRepositoryLink(urlRepository: Repository) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository.htmlUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    private fun openBrowser(urlRepository: Repository) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository.htmlUrl)
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null

    }

}