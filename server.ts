import express from "express";
import path from "path";

const app = express();
const PORT = 3000;

app.get("*", (req, res) => {
  res.send(`
    <html>
      <head>
        <title>Sahyadri-Samrakshane - Dev Status</title>
        <style>
          body { font-family: sans-serif; display: flex; align-items: center; justify-content: center; height: 100vh; background: #F5F5DC; color: #2D5A27; }
          .container { text-align: center; padding: 2rem; border: 2px solid #2D5A27; border-radius: 12px; }
        </style>
      </head>
      <body>
        <div class="container">
          <h1>Sahyadri-Samrakshane (Forest Sentinel)</h1>
          <p>Phase 7: Offline Support Complete.</p>
          <p>Features: WorkManager Sync, Local Persistence with Room, Retry Strategy, Network-aware scheduling.</p>
          <p>Architecture: SyncWorker implementation, Application-state persistence.</p>
          <p>Status: Offline-first flow ready. Next: Firebase Backend (Firestore & Storage).</p>
        </div>
      </body>
    </html>
  `);
});

app.listen(PORT, "0.0.0.0", () => {
  console.log(\`Server running on port \${PORT}\`);
});
