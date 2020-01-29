package ir.alirezaiyan.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(baseContext)
        recyclerview.adapter = Adapter()
    }


    class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount() = 50

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind("Test")
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textVIew: TextView by lazy {
                itemView.findViewById<TextView>(R.id.textView)
            }

            fun onBind(txt: String) {
                textVIew.text = txt
            }

        }
    }
}
