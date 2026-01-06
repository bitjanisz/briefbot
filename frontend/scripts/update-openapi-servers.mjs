#!/usr/bin/env node
import fs from 'node:fs/promises';
import path from 'node:path';
import process from 'node:process';
import dotenv from 'dotenv';

// Determine mode: CLI --mode/--mode=/-m/-m=, then env MODE, then NODE_ENV, default 'production'
const args = process.argv.slice(2);
function parseModeArg(argv) {
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a === '--mode' || a === '-m') {
      return argv[i + 1];
    }
    if (a.startsWith('--mode=')) {
      return a.split('=')[1];
    }
    if (a.startsWith('-m=')) {
      return a.split('=')[1];
    }
  }
  return undefined;
}
let mode = parseModeArg(args) || process.env.MODE || process.env.NODE_ENV || 'production';
console.log(`[update-openapi-servers] Using mode: ${mode}`);

const rootDir = process.cwd();

// Load env files with Vite-like precedence: .env, .env.local, .env.[mode], .env.[mode].local
// Last one wins. We'll manually parse files into a local object rather than mutating process.env.
const envFiles = [
  path.join(rootDir, '.env'),
  path.join(rootDir, '.env.local'),
  path.join(rootDir, `.env.${mode}`),
  path.join(rootDir, `.env.${mode}.local`),
];

const env = {};
for (const file of envFiles) {
  try {
    const content = await fs.readFile(file, 'utf8');
    const parsed = dotenv.parse(content);
    Object.assign(env, parsed); // later files override earlier ones
  } catch (err) {
    // Ignore missing files
    if (err && err.code !== 'ENOENT') {
      console.warn(`[update-openapi-servers] Warning reading ${file}:`, err.message);
    }
  }
}

// Allow explicit process.env to override files
if (process.env.VITE_SERVER_URL) {
  env.VITE_SERVER_URL = process.env.VITE_SERVER_URL;
}

const serverUrl = env.VITE_SERVER_URL;
if (!serverUrl) {
  console.error(
    `[update-openapi-servers] VITE_SERVER_URL not found for mode "${mode}". Checked files: ${envFiles
      .map((p) => path.basename(p))
      .join(', ')}. Set MODE or use --mode and ensure the env file defines VITE_SERVER_URL.`
  );
  process.exit(1);
}

// Basic URL validation
try {
  new URL(serverUrl);
} catch {
  console.error(`[update-openapi-servers] VITE_SERVER_URL is not a valid URL: ${serverUrl}`);
  process.exit(1);
}

const apiDocsPath = path.join(rootDir, 'src', 'api-docs', 'api-docs.json');

async function run() {
  let text;
  try {
    text = await fs.readFile(apiDocsPath, 'utf8');
  } catch (e) {
    console.error(`[update-openapi-servers] Cannot read ${apiDocsPath}:`, e.message);
    process.exit(1);
  }

  let json;
  try {
    json = JSON.parse(text);
  } catch (e) {
    console.error(`[update-openapi-servers] Invalid JSON in ${apiDocsPath}:`, e.message);
    process.exit(1);
  }

  if (!json.servers || !Array.isArray(json.servers)) {
    json.servers = [];
  }
  if (!json.servers[0] || typeof json.servers[0] !== 'object') {
    json.servers[0] = {};
  }

  json.servers[0].url = serverUrl;
  if (!json.servers[0].description) {
    json.servers[0].description = 'Generated server url';
  }

  const updated = JSON.stringify(json, null, 2) + '\n';
  try {
    await fs.writeFile(apiDocsPath, updated, 'utf8');
  } catch (e) {
    console.error(`[update-openapi-servers] Failed to write ${apiDocsPath}:`, e.message);
    process.exit(1);
  }

  console.log(`[update-openapi-servers] Updated servers[0].url to ${serverUrl} for mode ${mode}`);
}

run();
