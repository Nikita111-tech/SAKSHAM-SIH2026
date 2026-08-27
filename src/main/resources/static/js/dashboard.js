const questions = [
{q:"What is full form of NSSO?", a:["National Sample Survey Office","National Statistical System Office","National Survey Org"], ans:0},
{q:"Base year for new GDP series?", a:["2004-05","2011-12","2022-23"], ans:1},
{q:"PLFS stands for?", a:["Periodic Labour Force Survey","Public Labour Force System","..."], ans:0},
{q:"Which sampling is used in NSS?", a:["Simple Random","Stratified Multi-stage","Cluster"], ans:1},
{q:"SNA 2008 is related to?", a:["National Accounts","Survey Agency","National Stats"], ans:0}
];

function loadQuiz(){
 let html=""; 
 questions.forEach((q,i)=>{ 
   html+=`<div class="mb-4 p-3 bg-white/5 rounded-xl"><p class="font-bold mb-2">${i+1}. ${q.q}</p>`; 
   q.a.forEach((opt,j)=>{ html+=`<label class="block text-sm py-1"><input type="radio" name="q${i}" value="${j}" class="mr-2"> ${opt}</label>`}); 
   html+=`</div>`
 }); 
 document.getElementById("quiz").innerHTML=html;
}

function submitQuiz(){
 let score=0; 
 questions.forEach((q,i)=>{ let sel=document.querySelector(`input[name="q${i}"]:checked`); if(sel && parseInt(sel.value)==q.ans) score++; });
 let pct=Math.round(score/questions.length*100);
 document.getElementById("commScore").innerText="80%";
 document.getElementById("techScore").innerText=pct+"%";
 document.getElementById("teamScore").innerText="65%";
 const res=document.getElementById("result");
 res.classList.remove("hidden");
 res.innerHTML=`<p class="text-xl text-green-400 font-bold">Your Score: ${pct}% - Gap Analyzed!</p><p class="text-sm mt-2">Communication: High Gap, Technical: Low Gap</p>`;
 
 new Chart(document.getElementById("gapChart"),{type:'radar',data:{labels:['Communication','Technical','Teamwork','NSSO','Statistics'],datasets:[{label:'Your Score',data:[80,pct,65,pct,70],backgroundColor:'rgba(168,85,247,0.3)',borderColor:'#a855f7'}]},options:{scales:{r:{grid:{color:'rgba(255,255,255,0.1)'},ticks:{display:false}}}}});

 fetch('/api/submit-test',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({name:"Test User",email:"test@sih.com",comm:80,tech:pct})}).then(()=>console.log("saved"));
}

document.addEventListener("DOMContentLoaded", loadQuiz);
