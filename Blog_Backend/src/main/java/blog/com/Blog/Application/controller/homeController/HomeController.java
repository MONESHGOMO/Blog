package blog.com.Blog.Application.controller.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    @ResponseBody
    public String getHome(){
        return
                """
                        <!DOCTYPE html>
                                        <html lang="en">
                                        <head>
                                            <meta charset="UTF-8">
                                            <title>Dev16 - Backend Status</title>
                                            <meta name="viewport" content="width=device-width, initial-scale=1">
                                            <link rel="icon" href="https://i.ibb.co/wNzLMLnT/database.png" type="image/png" sizes="512x512" />
                                            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
                                            <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@300;400;500;600;700&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
                                            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
                                            <style>
                                                :root {
                                                    --primary: #6366f1;
                                                    --primary-dark: #4f46e5;
                                                    --secondary: #0ea5e9;
                                                    --dark: #1e293b;
                                                    --light: #f8fafc;
                                                    --success: #10b981;
                                                    --card-bg: rgba(255, 255, 255, 0.95);
                                                    --terminal-bg: #0f172a;
                                                    --card-border: rgba(255, 255, 255, 0.5);
                                                    --accent-gradient: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
                                                }
                                                body {
                                                    margin: 0;
                                                    padding: 0;
                                                    min-height: 100vh;
                                                    display: flex;
                                                    justify-content: center;
                                                    align-items: center;
                                                    font-family: 'Inter', sans-serif;
                                                    background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
                                                    color: var(--dark);
                                                    position: relative;
                                                    overflow-x: hidden;
                                                }
                                                .bg-animation {
                                                    position: fixed;
                                                    top: 0;
                                                    left: 0;
                                                    width: 100%;
                                                    height: 100%;
                                                    z-index: -2;
                                                }
                                                .bg-animation span {
                                                    position: absolute;
                                                    width: 20px;
                                                    height: 20px;
                                                    background: var(--primary);
                                                    opacity: 0.1;
                                                    border-radius: 50%;
                                                    animation: float 15s linear infinite;
                                                }
                                                @keyframes float {
                                                    0% { transform: translateY(0) rotate(0deg); opacity: 0; }
                                                    10% { opacity: 0.1; }
                                                    90% { opacity: 0.1; }
                                                    100% { transform: translateY(-1000%) rotate(720deg); opacity: 0; }
                                                }
                                                .status-card {
                                                    background: var(--card-bg);
                                                    backdrop-filter: blur(12px);
                                                    border-radius: 20px;
                                                    box-shadow: 0 15px 35px -12px rgba(0, 0, 0, 0.25), 0 0 0 1px var(--card-border);
                                                    padding: 2rem 2.5rem;
                                                    text-align: center;
                                                    max-width: 700px;
                                                    width: 90%;
                                                    position: relative;
                                                    overflow: hidden;
                                                    animation: cardEntrance 0.8s ease-out;
                                                    margin: 1.5rem 0;
                                                }
                                                @keyframes cardEntrance {
                                                    0% { opacity: 0; transform: translateY(30px) scale(0.95); }
                                                    100% { opacity: 1; transform: translateY(0) scale(1); }
                                                }
                                                .status-card::before {
                                                    content: '';
                                                    position: absolute;
                                                    top: 0;
                                                    left: 0;
                                                    width: 100%;
                                                    height: 5px;
                                                    background: var(--accent-gradient);
                                                }
                                                .status-indicator {
                                                    display: inline-flex;
                                                    align-items: center;
                                                    background: rgba(16, 185, 129, 0.1);
                                                    color: var(--success);
                                                    padding: 0.5rem 1.2rem;
                                                    border-radius: 50px;
                                                    font-size: 0.85rem;
                                                    font-weight: 600;
                                                    margin-bottom: 1.5rem;
                                                    font-family: 'JetBrains Mono', monospace;
                                                    border: 1px solid rgba(16, 185, 129, 0.2);
                                                }
                                                .status-dot {
                                                    height: 10px;
                                                    width: 10px;
                                                    background-color: var(--success);
                                                    border-radius: 50%;
                                                    margin-right: 8px;
                                                    position: relative;
                                                }
                                                .status-dot::after {
                                                    content: '';
                                                    position: absolute;
                                                    top: -3px;
                                                    left: -3px;
                                                    right: -3px;
                                                    bottom: -3px;
                                                    border-radius: 50%;
                                                    background-color: var(--success);
                                                    opacity: 0.4;
                                                    animation: pulse 2s infinite;
                                                }
                                                @keyframes pulse {
                                                    0% { transform: scale(0.8); opacity: 0.6; }
                                                    70% { transform: scale(1.4); opacity: 0; }
                                                    100% { transform: scale(0.8); opacity: 0; }
                                                }
                                                h1 {
                                                    color: var(--dark);
                                                    font-weight: 800;
                                                    margin-bottom: 1rem;
                                                    font-size: 2.2rem;
                                                    background: var(--accent-gradient);
                                                    -webkit-background-clip: text;
                                                    -webkit-text-fill-color: transparent;
                                                    background-clip: text;
                                                }
                                                .lead {
                                                    color: #64748b;
                                                    margin-bottom: 1.5rem;
                                                    line-height: 1.5;
                                                    font-size: 1rem;
                                                }
                                                .time-display {
                                                    font-family: 'JetBrains Mono', monospace;
                                                    color: var(--primary);
                                                    background: rgba(99, 102, 241, 0.1);
                                                    padding: 0.6rem 1rem;
                                                    border-radius: 10px;
                                                    margin: 1rem 0;
                                                    display: inline-block;
                                                    border: 1px solid rgba(99, 102, 241, 0.2);
                                                    font-size: 0.9rem;
                                                }
                                                .terminal {
                                                    background: var(--terminal-bg);
                                                    border-radius: 14px;
                                                    padding: 1.5rem;
                                                    margin: 1.5rem 0;
                                                    text-align: left;
                                                    font-family: 'JetBrains Mono', monospace;
                                                    color: #e2e8f0;
                                                    position: relative;
                                                    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
                                                    border: 1px solid rgba(255, 255, 255, 0.1);
                                                }
                                                .terminal-header {
                                                    display: flex;
                                                    align-items: center;
                                                    margin-bottom: 1rem;
                                                }
                                                .terminal-dots {
                                                    display: flex;
                                                    gap: 6px;
                                                }
                                                .terminal-dot {
                                                    width: 12px;
                                                    height: 12px;
                                                    border-radius: 50%;
                                                }
                                                .terminal-dot.red { background: #f87171; }
                                                .terminal-dot.yellow { background: #fbbf24; }
                                                .terminal-dot.green { background: #10b981; }
                                                .terminal-title {
                                                    position: absolute;
                                                    left: 0;
                                                    right: 0;
                                                    text-align: center;
                                                    color: #94a3b8;
                                                    font-size: 0.85rem;
                                                    font-weight: 500;
                                                }
                                                .terminal-content {
                                                    line-height: 1.5;
                                                    font-size: 0.9rem;
                                                }
                                                .terminal-prompt {
                                                    color: var(--secondary);
                                                    margin-right: 6px;
                                                    font-weight: 600;
                                                }
                                                .terminal-command {
                                                    color: #e2e8f0;
                                                    font-weight: 500;
                                                }
                                                .terminal-response {
                                                    color: #94a3b8;
                                                    margin-top: 8px;
                                                    font-weight: 400;
                                                }
                                                .blog-link {
                                                    display: inline-flex;
                                                    align-items: center;
                                                    color: var(--primary-dark);
                                                    font-weight: 600;
                                                    text-decoration: none;
                                                    transition: all 0.3s ease;
                                                    padding: 0.6rem 1.2rem;
                                                    border-radius: 10px;
                                                    background: rgba(99, 102, 241, 0.1);
                                                    border: 1px solid rgba(99, 102, 241, 0.2);
                                                    font-size: 0.9rem;
                                                    margin: 0.5rem 0;
                                                }
                                                .blog-link:hover {
                                                    color: white;
                                                    background: var(--primary);
                                                    transform: translateY(-2px);
                                                    box-shadow: 0 6px 16px rgba(99, 102, 241, 0.3);
                                                }
                                                .blog-link i {
                                                    margin-left: 6px;
                                                    font-size: 0.8rem;
                                                }
                                                .happy-coding {
                                                    color: var(--primary);
                                                    font-weight: 700;
                                                    font-size: 1.1rem;
                                                    margin: 1.5rem 0 1rem;
                                                    font-family: 'JetBrains Mono', monospace;
                                                }
                                                .social-icons {
                                                    display: flex;
                                                    justify-content: center;
                                                    gap: 0.8rem;
                                                    margin: 1.5rem 0;
                                                }
                                                .social-icons a {
                                                    display: inline-flex;
                                                    align-items: center;
                                                    justify-content: center;
                                                    width: 40px;
                                                    height: 40px;
                                                    background: rgba(99, 102, 241, 0.1);
                                                    color: var(--primary);
                                                    border-radius: 12px;
                                                    font-size: 1.1rem;
                                                    transition: all 0.3s ease;
                                                    border: 1px solid rgba(99, 102, 241, 0.15);
                                                }
                                                .social-icons a:hover {
                                                    background: var(--primary);
                                                    color: white;
                                                    transform: translateY(-3px) rotate(5deg);
                                                    box-shadow: 0 8px 20px rgba(99, 102, 241, 0.4);
                                                }
                                                .footer-text {
                                                    margin-top: 1.5rem;
                                                    color: #94a3b8;
                                                    font-size: 0.85rem;
                                                }
                                                @media (max-width: 768px) {
                                                    .status-card {
                                                        padding: 1.5rem 1.2rem;
                                                        margin: 1rem 0;
                                                    }
                                                    h1 {
                                                        font-size: 1.8rem;
                                                    }
                                                    .terminal {
                                                        padding: 1rem;
                                                    }
                                                    .terminal-content {
                                                        font-size: 0.8rem;
                                                    }
                                                    .social-icons a {
                                                        width: 35px;
                                                        height: 35px;
                                                        font-size: 1rem;
                                                    }
                                                }
                                            </style>
                                        </head>
                                        <body>
                                            <div class="bg-animation"></div>
                                            <div class="status-card container">
                                                <div class="status-indicator">
                                                    <div class="status-dot"></div>
                                                    STATUS: OPERATIONAL
                                                </div>
                                                <h1>Dev16 Blog Backend</h1>
                                                <p class="lead">All systems are running smoothly. API endpoints are responding now</p>
                                                <div class="time-display">
                                                    <span id="india-time">Loading IST time...</span>
                                                </div>
                                                <div class="terminal">
                                                    <div class="terminal-header">
                                                        <div class="terminal-dots">
                                                            <div class="terminal-dot red"></div>
                                                            <div class="terminal-dot yellow"></div>
                                                            <div class="terminal-dot green"></div>
                                                        </div>
                                                        <div class="terminal-title">Dev16 Server Status</div>
                                                    </div>
                                                    <div class="terminal-content">
                                                        <div>
                                                            <span class="terminal-prompt">$</span>
                                                            <span class="terminal-command">curl https://blog-1fcl.onrender.com/status</span>
                                                        </div>
                                                        <div class="terminal-response">
                                                            {"status": "OK", "timestamp": "<span id="api-timestamp">Loading timestamp...</span>", "version": "3.0"}
                                                        </div>
                                                    </div>
                                                </div>
                                                <p>
                                                    <a href="https://dev16-blog.web.app/" target="_blank" class="blog-link">
                                                        Visit Dev-16 Blog <i class="fas fa-arrow-up-right-from-square"></i>
                                                    </a>
                                                </p>
                                                <p class="happy-coding">Keep coding & building ✨</p>
                                                <div class="social-icons">
                                                    <a href="https://github.com/MONESHGOMO" target="_blank" aria-label="GitHub">
                                                        <i class="fab fa-github"></i>
                                                    </a>
                                                    <a href="https://www.youtube.com/@Moneshgomo" aria-label="YouTube">
                                                        <i class="fab fa-youtube"></i>
                                                    </a>
                                                </div>
                                                <p class="footer-text">
                                                    &copy; <span id="current-year">2023</span> Dev16 Blog | Server monitoring active
                                                </p>
                                            </div>
                                            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                                            <script>
                                                const bgAnimation = document.querySelector('.bg-animation');
                                                for (let i = 0; i < 12; i++) {
                                                    const span = document.createElement('span');
                                                    span.style.left = Math.random() * 100 + '%';
                                                    span.style.top = Math.random() * 100 + '%';
                                                    span.style.width = Math.random() * 15 + 8 + 'px';
                                                    span.style.height = span.style.width;
                                                    span.style.animationDelay = Math.random() * 8 + 's';
                                                    span.style.animationDuration = Math.random() * 15 + 8 + 's';
                                                    span.style.background = i % 3 === 0 ? 'var(--primary)' : (i % 3 === 1 ? 'var(--secondary)' : 'var(--success)');
                                                    bgAnimation.appendChild(span);
                                                }
                                                function getIndiaTime() {
                                                    const now = new Date();
                                                    const utc = now.getTime() + now.getTimezoneOffset() * 60000;
                                                    const ist = new Date(utc + 5.5 * 3600000);
                                                    return ist.toLocaleString('en-IN', {
                                                        year: 'numeric',\s
                                                        month: 'short',\s
                                                        day: 'numeric',
                                                        hour: '2-digit',\s
                                                        minute: '2-digit',\s
                                                        second: '2-digit'
                                                    }) + " IST";
                                                }
                                                function getIndiaTimestamp() {
                                                    const now = new Date();
                                                    const utc = now.getTime() + now.getTimezoneOffset() * 60000;
                                                    const ist = new Date(utc + 5.5 * 3600000);
                                                    const year = ist.getFullYear();
                                                    const month = String(ist.getMonth() + 1).padStart(2, '0');
                                                    const day = String(ist.getDate()).padStart(2, '0');
                                                    const hours = String(ist.getHours()).padStart(2, '0');
                                                    const minutes = String(ist.getMinutes()).padStart(2, '0');
                                                    const seconds = String(ist.getSeconds()).padStart(2, '0');
                                                    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}+05:30`;
                                                }
                                                function updateTimeDisplays() {
                                                    document.getElementById('india-time').textContent = getIndiaTime();
                                                    document.getElementById('api-timestamp').textContent = getIndiaTimestamp();
                                                }
                                                document.getElementById('current-year').textContent = new Date().getFullYear();
                                                updateTimeDisplays();
                                                setInterval(updateTimeDisplays, 1000);
                                            </script>
                                        </body>
                                        </html>
                        """;
    }
}
