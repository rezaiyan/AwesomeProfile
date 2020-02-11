package ir.alirezaiyan.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(baseContext)
        recyclerview.adapter = Adapter()

        newrelease.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("http://github.com/rezaiyan/awesomeprofile")
            }.also { startActivity(it) }
        }
    }


    class Adapter() : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layout = when (viewType) {
                0 -> R.layout.item1
                1 -> R.layout.item2
                else -> R.layout.item3
            }

            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    layout,
                    parent,
                    false
                )
            )
        }

        override fun getItemViewType(position: Int) = position

        override fun getItemCount() = 3

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
